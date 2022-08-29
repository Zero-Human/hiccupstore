package hiccup.hiccupstore.board.controller;

import hiccup.hiccupstore.board.dto.Board;
import hiccup.hiccupstore.board.dto.BoardWriteForm;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.dto.Review;
import hiccup.hiccupstore.board.service.BoardService;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {
    private final FileStore fileStore;
    private final BoardService boardService;
    @GetMapping("api/productQnA")
    public Map<String,Object> getProductQnA(@RequestParam("boardId")Integer boardId){
        Map<String,Object> product = new HashMap<>();
        product.put("productQnA",boardService.getProductQnAById(boardId));
        product.put("imageNameList",boardService.getImageListNameByBoardId(boardId));
        return product;
    }
    @GetMapping("api/review")
    public String getReview(Model model,
                            @RequestParam("productId")Integer pid,
                            @RequestParam("pageNum")Integer pageNum){
        ArrayList<Review> reviewList = boardService.getReviewByProduct(pid) ;
        Map<Integer, ArrayList<String>> imageNameList = new HashMap<>();

        model.addAttribute("reviewList", boardService.getReviewByProduct(pid));
        for(Review review : reviewList ){
            imageNameList.put(review.getBoardId(), boardService.getImageListNameByBoardId(review.getBoardId()));
        }
        model.addAttribute("imageNameList", imageNameList);
        return "/product/productReview";
    }

    @PostMapping("api/review/add")
    public void addReview(@ModelAttribute BoardWriteForm boardWriteForm,
                          @RequestParam( value = "images",required = false) ArrayList<MultipartFile> images) throws IOException {
        //FIXME userId
        ArrayList<Image> imageList = null;
        if(!images.get(0).getOriginalFilename().equals("")){
            List<UploadFile> uploadImages = fileStore.storeFiles(images);
            imageList = new ArrayList<>();
            for (UploadFile item : uploadImages) {
                imageList.add(Image.builder().productId(boardWriteForm.getProductId()).
                        imageName(item.getStoreFileName()).
                        imagePath(fileStore.getFullPath(item.getStoreFileName())).build());

            }
        }
        boardService.insertReview(boardWriteForm.toReview(1),imageList);
    }
    @PostMapping("api/review/edit")
    public void editReview(@ModelAttribute BoardWriteForm boardWriteForm,
                          @RequestParam(value = "preImages") ArrayList<String> preImages,
                          @RequestParam( value = "images",required = false) ArrayList<MultipartFile> images) throws IOException {
        //FIXME userId
        //FIXME 수정해야한다.
    }
    @PostMapping("api/review/delete")
    public void deleteReview(@RequestParam("boardId") Integer boardId){
        ArrayList<String> imageListName = boardService.getImageListNameByBoardId(boardId);
        for (String imageName: imageListName) {
            fileStore.deleteFile(fileStore.getFullPath(imageName));
        }
        boardService.deleteImageByBoardId(boardId);
        boardService.deleteReview(boardId);
    }

    @GetMapping("/board/{filename}")
    public Resource viewImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileStore.getFullPath(filename));
    }
}
