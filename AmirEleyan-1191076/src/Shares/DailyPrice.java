/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At:  10/4/2021  1:20 AM
 */
package Shares;

public class DailyPrice implements Comparable<DailyPrice> {
    private String companyName;
    private float sharesSalePrice;

    /**
     * No arg constructor
     */
    public DailyPrice() {
    }

    /**
     * Constructor with specific arguments
     */
    public DailyPrice(String companyName, float sharesPrice) {
        this.companyName = companyName;
        this.sharesSalePrice = sharesPrice;
    }

    /**
     * Constructor with dataLine
     */
    public DailyPrice(String dataLine) {
        int q = dataLine.indexOf(',');
        this.companyName = dataLine.substring(0, q).trim();
        this.sharesSalePrice = Float.parseFloat(dataLine.substring(q + 1).trim());
    }


    /**
     * return company name
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Set a new company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Return a shares price
     */
    public float getSharesSalePrice() {
        return this.sharesSalePrice;
    }

    /**
     * Set a new shares
     */
    public void setSharesSalePrice(float sharesSalePrice) {
        this.sharesSalePrice = sharesSalePrice;
    }

    /**
     * Return the data as string
     */
    @Override
    public String toString() {
        return "CompanyName: " + companyName + ", sharesPrice: " + sharesSalePrice + '\n';
    }

    /**
     * To check if two onj has same companyName
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DailyPrice)
            return (this.companyName.equals(((DailyPrice) obj).companyName));
        return false;
    }

    /**
     * CompareTo between tow obj by company name
     */
    @Override
    public int compareTo(DailyPrice o) {
        return (this.companyName.compareTo(o.companyName));
    }
}
