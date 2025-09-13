package tech.zeta.dto;

public class ReportDTO {
        private String categoryName;
        private double totalAmount;

        public ReportDTO(String categoryName, double totalAmount) {
            this.categoryName = categoryName;
            this.totalAmount = totalAmount;
        }

        public String getCategoryName() { return categoryName; }
        public double getTotalAmount() { return totalAmount; }

        @Override
        public String toString() {
            return categoryName + " : " + totalAmount;
        }
}
