package hiccup.hiccupstore.board.controller;

import hiccup.hiccupstore.board.dto.BoardWriteForm;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.service.BoardService;
import hiccup.hiccupstore.product.dto.ProductImage;
import hiccup.hiccupstore.product.util.FileStore;
import hiccup.hiccupstore.product.util.ImageType;
import hiccup.hiccupstore.product.util.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final FileStore fileStore;
    private final BoardService boardService;
    // 상품, 리뷰 작성
    @GetMapping("test")
    public String index(){
        return "product/QnA";
    }
    @PostMapping("/board/productQnA/add")
    public String addProductQnA(@ModelAttribute BoardWriteForm boardWriteForm,
                                @RequestParam( value = "images",required = false) ArrayList<MultipartFile>  images) throws IOException {
        //FIXME 유저 ID를 추가해야한다.
        List<UploadFile> uploadImages = fileStore.storeFiles(images);
        ArrayList<Image> imageList = new ArrayList<>();
        for (UploadFile item : uploadImages) {
            imageList.add(Image.builder().productId(boardWriteForm.getProductId()).
                    ImageName(item.getStoreFileName()).
                    ImagePath(fileStore.getFullPath(item.getStoreFileName())).build());

        }
        if(uploadImages.size() == 0){
            imageList = null;
        }
        boardService.insertProductQnA(boardWriteForm.toProductQnA(1), imageList);

        return String.format("redirect:/product/detail?pid=%d",boardWriteForm.getProductId());
    }
    @PostMapping("/board/productQnA/edit")
    public String editProductQnA(@ModelAttribute BoardWriteForm boardWriteForm,
                                @RequestParam( value = "boardId") Integer boardId,
                                @RequestParam( value = "preImages", required = false) ArrayList<String> preImages,
                                @RequestParam( value = "images",required = false) ArrayList<MultipartFile>  images) throws IOException {
        //FIXME 유저 ID를 추가해야한다.
        ArrayList<Image> imageList =null;
        if(!images.get(0).getOriginalFilename().equals("")){
            imageList = new ArrayList<>();
            List<UploadFile> uploadImages = fileStore.storeFiles(images);
            for (UploadFile item : uploadImages) {
                imageList.add(Image.builder().productId(boardWriteForm.getProductId()).
                        ImageName(item.getStoreFileName()).
                        ImagePath(fileStore.getFullPath(item.getStoreFileName())).build());
            }
            for (String image : preImages) {
                fileStore.deleteFile(fileStore.getFullPath(image));
            }
        }
        boardService.editProductQnA(boardWriteForm.toProductQnA(1), imageList);

        return String.format("redirect:/product/detail?pid=%d",boardWriteForm.getProductId());
    }
    @PostMapping("/board/productQnA/delete")
    public String deleteProductQnA( @RequestParam( value = "boardId") Integer boardId,
                                    @RequestParam( value = "productId") Integer productId){
        ArrayList<String> imageListName = boardService.getImageListNameByBoardId(boardId);
        for (String imageName: imageListName) {
            fileStore.deleteFile(fileStore.getFullPath(imageName));
        }
        boardService.deleteProductQnA(boardId);
        boardService.deleteImageByBoardId(boardId);
        return String.format("redirect:/product/detail?pid=%d",productId);
    }
    @GetMapping("api/productQnAList")
    public String getProductQnAList(){

        return "product/QnA";
    }

}
