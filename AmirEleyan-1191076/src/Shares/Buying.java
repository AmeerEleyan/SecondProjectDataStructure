/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At:  10/4/2021  2:11 AM
 */
package Shares;

public class Buying implements Comparable<Buying> {

    /**
     * Attribute
     */
    private int numberOfShares;
    private float sharesBuyingPrice;
    private String companyName;
    private int date, month, year;
    private String stringDate;

    // No arg constructor
    public Buying() {
    }

    // Constructor with specific data
    public Buying(int numberOfShares, float sharesBuyingPrice,
                  String companyName, int date, int month, int year) {
        this.numberOfShares = numberOfShares;
        this.sharesBuyingPrice = sharesBuyingPrice;
        this.companyName = companyName;
        this.date = date;
        this.month = month;
        this.year = year;
        this.stringDate = this.date + "/" + this.month + "/" + this.year;
    }

    public Buying(String dataLine) {

        // Number of shares
        int q = dataLine.indexOf(',');
        this.numberOfShares = Integer.parseInt(dataLine.substring(0, q).trim());

        // shares buying price
        dataLine = dataLine.substring(q + 1);
        q = dataLine.indexOf(',');
        this.sharesBuyingPrice = Float.parseFloat(dataLine.substring(0, q).trim());

        // company name
        dataLine = dataLine.substring(q + 1);
        q = dataLine.indexOf(',');
        this.companyName = dataLine.substring(0, q).trim();

        // date
        dataLine = dataLine.substring(q + 1);
        q = dataLine.indexOf('/');
        this.date = Integer.parseInt(dataLine.substring(0, q).trim());

        // month
        dataLine = dataLine.substring(q + 1);
        q = dataLine.indexOf('/');
        this.month = Integer.parseInt(dataLine.substring(0, q).trim());

        // year
        this.year = Integer.parseInt(dataLine.substring(q + 1).trim());

        this.stringDate = this.date + "/" + this.month + "/" + this.year;

    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public float getSharesBuyingPrice() {
        return this.sharesBuyingPrice;
    }

    public void setSharesBuyingPrice(float sharesBuyingPrice) {
        this.sharesBuyingPrice = sharesBuyingPrice;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // return the date
    public int getDate() {
        return this.date;
    }

    // set a new date
    public void setDate(int date) {
        this.date = date;
    }

    // return the month
    public int getMonth() {
        return this.month;
    }

    //  set a new month
    public void setMonth(int month) {
        this.month = month;
    }

    // return the year
    public int getYear() {
        return this.year;
    }

    // set a new year
    public void setYear(int year) {
        this.year = year;
    }

    // return tha date as a string
    public String getStringDate() {
        return this.stringDate;
    }

    /**
     * return the data in this onj as a string
     */
    @Override
    public String toString() {
        return "numberOfShares: " + numberOfShares +
                ", sharesBuyingPrice: " + sharesBuyingPrice +
                ", companyName: " + companyName + ", date: " + date +
                "/" + month + "/" + year + '\n';
    }

    /**
     * Check tow obj if has same companyName
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Buying)
            return (this.companyName.equals(((Buying) obj).companyName));
        return false;
    }

    /**
     * Comapre between two obj depend on the date
     */
    @Override
    public int compareTo(Buying o) {
        if (this.year > o.year)
            return 1;
        else if (this.year == o.year && this.month > o.month)
            return 1;
        else if (this.year == o.year && this.month == o.month && this.date > o.date)
            return 1;
        else if (this.year == o.year && this.month == o.month && this.date == o.date)
            return 0;
        else return -1;
    }

}
