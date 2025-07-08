package com.example.myapplication.model;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockProductData {

    public static List<ProductModel> getFakeProducts() {
        List<ProductModel> list = new ArrayList<>();

        // ✅ Lấy danh sách brand giả lập từ MockBrandData
        List<Brand> brandList = MockBrandData.getMockBrands();

        for (int i = 0; i < brandList.size(); i++) {
            Brand brand = brandList.get(i);
            int productId = i + 1;

            ProductModel product = new ProductModel();

            product.setProductId(productId);
            product.setProductCode("P00" + productId);
            product.setProductRemain(100 + i);
            product.setProductRemainVn(50 + i);
            product.setProductRemainKr(30 + i);
            product.setMadeIn(i % 2 == 0 ? "Korea" : "Vietnam");
            product.setBuyCount(200 + i);
            product.setLikeNumber(40 + i);
            product.setCommentCount(10 + i);
            product.setAverageStar(4.0 + (i % 3) * 0.3);
            product.setPrice(100000 + i * 1000);
            product.setPriceVn(95000 + i * 800);
            product.setPriceKr(92000 + i * 700);
            product.setPriceSales(97000 + i * 1000);
            product.setPriceSalesVn(93000 + i * 900);
            product.setPriceSalesKr(90000 + i * 800);
            product.setPercentKol(10.0 + i);
            product.setPercentKolVn(9.0 + i);
            product.setPercentKolKr(8.0 + i);
            product.setProductName("Product " + productId);
            product.setProductDescription("Mô tả chi tiết sản phẩm số " + productId);
            product.setProductImages(new ArrayList<>(Arrays.asList(
                    "https://atinproduction.com/wp-content/uploads/2021/07/AWP01220-scaled-1280x1920.jpg" + productId + "_1.jpg",
                    "https://atinproduction.com/wp-content/uploads/2021/07/AWP01220-scaled-1280x1920.jpg" + productId + "_2.jpg",
                    "https://atinproduction.com/wp-content/uploads/2021/07/AWP01220-scaled-1280x1920.jpg" + productId + "_3.jpg",
                    "https://atinproduction.com/wp-content/uploads/2021/07/AWP01220-scaled-1280x1920.jpg" + productId + "_4.jpg"
            )));
            product.setFreeDelivery(i % 2 == 0);
            product.setFreeDeliveryVn(true);
            product.setFreeDeliveryKr(i % 2 == 1);
            product.setSupportBankTransfer(true);
            product.setSupportTransportInternational(i % 2 == 1);
            product.setActive(true);
            product.setMfgDate("2025-06-0" + productId);
            product.setInsertDateTime("2025-07-01 10:0" + productId + ":00");
            product.setUpdateDateTime("2025-07-01 12:0" + productId + ":00");

            // ✅ Gán brand từ danh sách
            product.setBrandId(brand.getBrandId());
            product.setBrand(brand);

            // Gán Category
            Category category = new Category();
            category.setCategoryId(i);
            category.setCategoryParentId(100 + i);
            category.setCategoryParentName("Parent Category " + i);
            category.setCategoryParentImage("https://example.com/parent_category" + i + ".jpg");
            category.setCategoryParentBackgroundColor(i % 2 == 0 ? "#FFE0B2" : "#BBDEFB");
            category.setCategoryName("Category " + i);
            category.setCategoryImage("https://example.com/category" + i + ".jpg");

            // Slide images giả lập
            ArrayList<Object> slideImages = new ArrayList<>();
            slideImages.add("https://example.com/slide" + i + "_1.jpg");
            slideImages.add("https://example.com/slide" + i + "_2.jpg");
            category.setCategorySlideImages(slideImages);

            // CategoryParentSlideImage giả lập
            ArrayList<CategoryParentSlideImage> parentSlides = new ArrayList<>();
            parentSlides.add(new CategoryParentSlideImage(i * 10 + 1, i, product.getProductId(), "https://example.com/parent_slide" + i + "_1.jpg"));
            parentSlides.add(new CategoryParentSlideImage(i * 10 + 2, i, product.getProductId(), "https://example.com/parent_slide" + i + "_2.jpg"));
            category.setCategoryParentSlideImages(parentSlides);

            product.setCategoryId(i);
            product.setCategory(category);

            // DynamicSizes
            List<DynamicSizes> sizeList = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                DynamicSizes size = new DynamicSizes();
                size.setDynamicSizeId(j);
                size.setDynamicSizeCode("SIZE_" + j);
                size.setPrice(100000 + j * 1000);
                size.setPriceVn(95000 + j * 900);
                size.setPriceKr(90000 + j * 800);

                size.setPercentSales(10.0 + j);
                size.setPercentSalesVn(9.0 + j);
                size.setPercentSalesKr(8.0 + j);

                size.setPercentKol(5.0 + j);
                size.setPercentKolVn(4.5 + j);
                size.setPercentKolKr(4.0 + j);

                size.setProductRemain(50 + j * 10);
                size.setProductRemainVn(30 + j * 5);
                size.setProductRemainKr(20 + j * 3);

                size.setPriceSales(90000 + j * 700);
                size.setPriceSalesVn(85000 + j * 600);
                size.setPriceSalesKr(80000 + j * 500);

                size.setPriceAfterSales(size.getPrice() - (int)(size.getPrice() * size.getPercentSales() / 100));
                size.setPriceAfterSalesVn(size.getPriceVn() - (int)(size.getPriceVn() * size.getPercentSalesVn() / 100));
                size.setPriceAfterSalesKr(size.getPriceKr() - (int)(size.getPriceKr() * size.getPercentSalesKr() / 100));

                sizeList.add(size);
            }
            product.setDynamicSizes(sizeList);

            // DynamicColors
            List<DynamicColors> colorList = new ArrayList<>();
            for (int k = 1; k <= 3; k++) {
                DynamicColors color = new DynamicColors();

                color.setDynamicColorId(k);
                color.setDynamicColorCode("COLOR_" + k);
                color.setDynamicColorName("Color Name " + k);
                color.setDynamicColorNameVn("Tên màu " + k);
                color.setDynamicColorNameKr("색상 " + k);

                color.setPrice(110000 + k * 1000);
                color.setPriceVn(105000 + k * 900);
                color.setPriceKr(100000 + k * 800);

                color.setPercentSales(12.0 + k);
                color.setPercentSalesVn(11.0 + k);
                color.setPercentSalesKr(10.0 + k);

                color.setPercentKol(6.0 + k);
                color.setPercentKolVn(5.5 + k);
                color.setPercentKolKr(5.0 + k);

                color.setProductRemain(60 + k * 10);
                color.setProductRemainVn(40 + k * 5);
                color.setProductRemainKr(30 + k * 3);

                color.setPriceSales(95000 + k * 700);
                color.setPriceSalesVn(90000 + k * 600);
                color.setPriceSalesKr(85000 + k * 500);

                color.setPriceAfterSales(color.getPrice() - (int)(color.getPrice() * color.getPercentSales() / 100));
                color.setPriceAfterSalesVn(color.getPriceVn() - (int)(color.getPriceVn() * color.getPercentSalesVn() / 100));
                color.setPriceAfterSalesKr(color.getPriceKr() - (int)(color.getPriceKr() * color.getPercentSalesKr() / 100));

                colorList.add(color);
            }
            product.setDynamicColors(colorList);

            list.add(product);
        }

        return list;
    }
}
