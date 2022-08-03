package hiccup.hiccupstore.product.dto.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page {
    private int pageCount ;
    private int startPage ;
    private int endPage ;
    private int lastEnd ;
    private int total ;
    private boolean prev, next ;
    private PageCriteria criteria ;

    public Page(int total, int pageCount, PageCriteria criteria) {
        this.total = total ;
        this.pageCount = pageCount; // 하단에 출력할 페이지 리스트 길이
        this.criteria = criteria ;

        // int 형인 PageNum을 우선 1.0을 곱해줘서 double(부동소숫점) 형으로 변환
        /*
        현재 페이지 / 출력 페이지 리스트 길이 * 출력 페이지 리스트 길이
         ex. 17.0/ 10 --(ceil(1.7))--> 2 * 10 --> 20 -> endPage
         ex. 2.0 / 10 --(ceil(0.2))--> 1 * 10 --> 10 -> endPage
         */
        this.endPage = (int)( Math.ceil( this.criteria.getPageNum()*1.0 / this.pageCount ) ) * this.pageCount ; // 조회된 페이지 기준으로 끝페이지 숫자 구하기

        /*
        ex. 20 - (10-1) --> 11
        ex. 10 - (10-1) --> 1
         */
        this.startPage = this.endPage - (this.pageCount-1);

        // 전체 데이터 갯수 / 한페이지에 출력될 데이터 개수
        /*
            1000개 데이터 16개씩 출력
            ex. 1000/16 -> 62.5 --(ceil)--> 63.0 -> 63
            10개 데이터 16개씩 출력
            ex. 10 / 16 ->0.625--(ceil)--> 1.000 -> 1
         */

        this.lastEnd = (int)( Math.ceil( this.total*1.0 / this.criteria.getAmountInOnePage() ) );

        /*
          endPage가 lastEnd보다 클 순 없음
          -> endPage가 더 커지면 lastEnd값으로 설정
          ( lastEnd가 63 인데 endPage가 70 * lastEnd는 0이 아닌 -> endPage = lastEnd)
          ( lasEnd가 0 : 데이터 아예 없음 -> "조회결과 없습니다" 출력할 한 페이지는 필요 -> endPage 1로 설정
         */
        if(this.endPage > this.lastEnd) {
            this.endPage = this.lastEnd == 0 ? 1 : this.lastEnd ;
        }

        this.prev = this.startPage > 1 ;
        this.next = this.endPage < this.lastEnd ;
    }
}
