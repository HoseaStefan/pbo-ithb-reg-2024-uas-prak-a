package model;

public class Transaction {
    private int transaction_id;
    private int customer_id;
    private String package_type;
    private double package_weight;
    private double total_cost;
    private String created_at;
    private String receipt_name;
    private String receipt_address;
    private String receipt_phone;

    public Transaction(int transaction_id, int customer_id, String package_type, double package_weight,
            double total_cost, String created_at, String receipt_name, String receipt_address, String receipt_phone) {
        this.transaction_id = transaction_id;
        this.customer_id = customer_id;
        this.package_type = package_type;
        this.package_weight = package_weight;
        this.total_cost = total_cost;
        this.created_at = created_at;
        this.receipt_name = receipt_name;
        this.receipt_address = receipt_address;
        this.receipt_phone = receipt_phone;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public double getPackage_weight() {
        return package_weight;
    }

    public void setPackage_weight(double package_weight) {
        this.package_weight = package_weight;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getReceipt_name() {
        return receipt_name;
    }

    public void setReceipt_name(String receipt_name) {
        this.receipt_name = receipt_name;
    }

    public String getReceipt_address() {
        return receipt_address;
    }

    public void setReceipt_address(String receipt_address) {
        this.receipt_address = receipt_address;
    }

    public String getReceipt_phone() {
        return receipt_phone;
    }

    public void setReceipt_phone(String receipt_phone) {
        this.receipt_phone = receipt_phone;
    }

}
