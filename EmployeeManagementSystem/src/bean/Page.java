package bean;

public class Page {
    private int currentPage;//当前页
    private int numPerPage;//每页显示的记录数
    private int totalPage;//总页数
    private int totalCount;//总记录数
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getNumPerPage() {
        return numPerPage;
    }
    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    @Override
    public String toString() {
        return "Page [currentPage=" + currentPage + ", numPerPage=" + numPerPage + ", totalPage=" + totalPage
                + ", totalCount=" + totalCount + "]";
    }
}
