package hiccup.hiccupstore.product.dto.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;


/*
@Data:
Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value
 */
@Data
public class PageCriteria {
    private int pageNum ; // 보여줄 페이지 번호
    private int amountInOnePage ; // x개씩 보기
    private String type = null ; // Type Parameter
    private Integer p = null; // priceRange Parameter
    private String sort; // 정렬기준

    public PageCriteria() {
        this.pageNum = 1 ;
        this.amountInOnePage = 16;
    }

    // liquor 조회 때 사용될 생성자
    public PageCriteria(int pageNum, int amountInOnePage, String type, String sort) {
        this.pageNum = pageNum;
        this.amountInOnePage = amountInOnePage;
        this.type = type;
        this.sort = sort ;
    }

    // price 조회 때 사용될 생성자
    public PageCriteria(int pageNum, int amountInOnePage, int p, String sort){
        this.pageNum = pageNum;
        this.amountInOnePage = amountInOnePage;
        this.p = p;
        this.sort = sort ;
    }


    public String getPagingListLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
        if( this.p == null) {
            builder.queryParam("pageNum", pageNum)
                    .queryParam("amountInOnePage", amountInOnePage)
                    .queryParam("type", type)
                    .queryParam("sort", sort);
        }else  {
            builder.queryParam("pageNum", pageNum)
                    .queryParam("amountInOnePage", amountInOnePage)
                    .queryParam("p", p)
                    .queryParam("sort", sort);
        }
        System.out.println(builder.toString() + " : " + builder.toUriString());
        return builder.toUriString();
    }
}
