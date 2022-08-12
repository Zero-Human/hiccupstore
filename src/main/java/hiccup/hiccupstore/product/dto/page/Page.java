package hiccup.hiccupstore.product.dto.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Page {

    private int currentPage ;
    private int startPage ;
    private int endPage ;
    private int lastestPage ;
    private int showPageCnt = 10; // 페이지 바에 보여질 페이지 갯수
    private int total ;
    private int cntPerPage;

    private boolean prev, next ;
    private ViewCriteria viewCriteria ;

    public Page(int total, int currentPage, int cntPerPage, ViewCriteria viewCriteria){
        this.currentPage = currentPage;
        this.cntPerPage = cntPerPage ;
        this.total = total;
        calcLastestPage( this.total, this.cntPerPage);
        calcStartEndPage( this.currentPage, this.showPageCnt );
        this.viewCriteria = viewCriteria ;
    }

    public void calcLastestPage(int total, int cntPerPage){
        setLastestPage( (int) Math.ceil( (double)total / (double) cntPerPage ) );
    }
    public void calcStartEndPage(int currentPage, int showPageCnt){
        setEndPage( (int) Math.ceil( (double)currentPage / (double) showPageCnt ) * showPageCnt );
        if( this.endPage > this.lastestPage ) {
            setEndPage( this.lastestPage == 0 ? 1 : this.lastestPage ) ;
        }

        setStartPage( this.endPage - (showPageCnt-1) );
        if ( this.startPage < 1) {
            setStartPage(1);
        }

        // 꺽쇠괄호 표시를 위한 Prev Next
        setPrev( this.startPage > 1 );
        setNext( this.endPage < this.lastestPage );
    }


}
