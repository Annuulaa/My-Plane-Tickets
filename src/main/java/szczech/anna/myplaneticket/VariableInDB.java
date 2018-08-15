
package szczech.anna.myplaneticket;


public class VariableInDB {
    
    private String fromWhere, where, date, price;
    
    public VariableInDB(String fromWhere, String where, String date, String price) {
        this.fromWhere = fromWhere;
        this.where = where;
        this.date = date;
        this.price = price;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public String getWhere() {
        return where;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
    
    
}
