package general.pom;


public abstract class PageSteps {


    protected void checkURL(String url, PageObject page) {
        String browserURL = (url != null) ? url : page.getPagePath();

    }


    public abstract void clicksOnIcon(String icon);
}
