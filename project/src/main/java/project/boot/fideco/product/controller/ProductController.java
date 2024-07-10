package project.boot.fideco.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.boot.fideco.product.entity.ProductEntity;
import project.boot.fideco.product.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/save")
    public String showCreateForm(Model model) {
        model.addAttribute("productEntity", new ProductEntity());
        return "product/save";
    }

    @PostMapping
    public String createProduct(@ModelAttribute ProductEntity productEntity,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            productService.saveProduct(productEntity, imageFile);
            return "redirect:/products/productList";
        } catch (IOException e) {
            return "error";
        }
    }
    // 관리자용
    @GetMapping("/productDetail/{id}")
    public String getProductDetail(@PathVariable("id") String id, Model model) {
        Optional<ProductEntity> productEntity = productService.getProductById(id);
        if (productEntity.isPresent()) {
            ProductEntity product = productEntity.get();
            model.addAttribute("product", product);
            model.addAttribute("productThumbnail", product.getProductImagePath());
            return "product/productDetail";
        } else {
            return "error";
        }
    }
    //회원용
    @GetMapping("/productOrder/{id}")
    public String getProductOrder(@PathVariable("id") String id, Model model) {
        Optional<ProductEntity> productEntity = productService.getProductById(id);
        if (productEntity.isPresent()) {
            ProductEntity product = productEntity.get();
            model.addAttribute("product", product);
            model.addAttribute("productThumbnail", product.getProductImagePath());
            return "product/productOrder";
        } else {
            return "error";
        }
    }
    @GetMapping("/productList")
    public String getAllProducts(Model model) {
        List<ProductEntity> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/productList";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        Optional<ProductEntity> productEntity = productService.getProductById(id);
        if (productEntity.isPresent()) {
            model.addAttribute("productEntity", productEntity.get());
            return "product/update";
        } else {
            return "error";
        }
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") String id, @ModelAttribute ProductEntity productEntity,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            productService.updateProduct(id, productEntity, imageFile);
            return "redirect:/products/productList";
        } catch (IOException | IllegalArgumentException e) {
            return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable("id") String id, Model model) {
        Optional<ProductEntity> productEntity = productService.getProductById(id);
        if (productEntity.isPresent()) {
            model.addAttribute("product", productEntity.get());
            return "product/delete";
        } else {
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
        return "redirect:/products/productList";
    }
}
