package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.ProductDto;
import hiccup.hiccupstore.user.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class Homecontroller {

    private final HomeService homeService;

    @GetMapping("/")
    public String home(Model model){

        List<ProductDto> productList = homeService.getProductDtoList();
        List<NoticeDto> noticeList = homeService.getNoticeDtoList();
        model.addAttribute("productlist",productList);
        model.addAttribute("noticelist",noticeList);

        return "index";
    }

    @GetMapping("/outstory")
    public String oustStory(){
        return "ourstory";
    }


}
