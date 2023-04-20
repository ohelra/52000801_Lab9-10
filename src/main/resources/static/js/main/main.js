function searchProductByCategory() {
  $("#search-all-categories").on("click", ".btn-search-category", function () {
    let categoryId = $(this).data("category-id");
    $("#list-product").empty();

    if (categoryId != 0) {
      fetch("/api/v1/product/find-by-category/" + categoryId)
        .then((response) => response.json())
        .then((response) => {
          if (response.success) {
            response.data.forEach((item) => {
              if (item.active == 1) {
                $("#list-product").append(`
                                    <div class="col-lg-3 col-md-6 col-sm-6">
                                        <div class="product__item">
                                            <div class="product__item__pic set-bg" data-setbg="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg">
                                                <img src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg" alt="${item.name}">
                                                <ul class="product__item__pic__hover">
                                                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                                    <li><a href="product/${item.id}"><i class="fa fa-eye"></i></a></li>
                                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                                </ul>
                                            </div>
                                            <div class="product__item__text">
                                                <h6><a href="#">${item.name}</a></h6>
                                                <h5>$${item.price}</h5>
                                            </div>
                                        </div>
                                    </div>
                                `);
              }
            });
          }
        });
    } else {
      fetch("/api/v1/product")
        .then((response) => response.json())
        .then((response) => {
          if (response.success) {
            response.data.forEach((item) => {
              if (item.active == 1) {
                $("#list-product").append(`
                                    <div class="col-lg-3 col-md-6 col-sm-6">
                                        <div class="product__item">
                                            <div class="product__item__pic set-bg" data-setbg="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg">
                                                <img src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg" alt="${item.name}">
                                                <ul class="product__item__pic__hover">
                                                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                                    <li><a href="product/${item.id}"><i class="fa fa-eye"></i></a></li>
                                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                                </ul>
                                            </div>
                                            <div class="product__item__text">
                                                <h6><a href="#">${item.name}</a></h6>
                                                <h5>$${item.price}</h5>
                                            </div>
                                        </div>
                                    </div>
                                `);
              }
            });
          }
        });
    }
  });
}

function menuCategory() {
  $("#search-all-categories").empty();
  fetch("/api/v1/category")
    .then((response) => response.json())
    .then((response) => {
      if (response.success) {
        $("#search-all-categories").append(
          `<li><a class="btn-search-category" type="button" data-category-id="0">All Categories</a></li>`
        );
        response.data.forEach((item) => {
          if (item.active == 1) {
            $("#search-all-categories").append(
              `<li><a class="btn-search-category" type="button" data-category-id="${item.id}">${item.name}</a></li>`
            );
          }
        });
      }
    });
  searchProductByCategory();
}

function sortByPrice() {
  $("#sort-price").on("click", "li", function () {
    $("#list-product").empty();

    let sortOption = $(this).data("sort_price");
    if (sortOption == "desc") {
      fetch("/api/v1/product/sort-by-price-asc")
        .then((response) => response.json())
        .then((response) => {
          if (response.success) {
            response.data.forEach((item) => {
              if (item.active == 1) {
                $("#list-product").append(`
                  <div class="col-lg-3 col-md-6 col-sm-6">
                      <div class="product__item">
                          <div class="product__item__pic set-bg" data-setbg="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg">
                              <img src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg" alt="${item.name}">
                              <ul class="product__item__pic__hover">
                                  <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                  <li><a href="product/${item.id}"><i class="fa fa-eye"></i></a></li>
                                  <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                              </ul>
                          </div>
                          <div class="product__item__text">
                              <h6><a href="#">${item.name}</a></h6>
                              <h5>$${item.price}</h5>
                          </div>
                      </div>
                  </div>
              `);
              }
            });
          }
        });
    } else if (sortOption == "asc") {
      fetch("/api/v1/product/sort-by-price-desc")
        .then((response) => response.json())
        .then((response) => {
          if (response.success) {
            response.data.forEach((item) => {
              if (item.active == 1) {
                $("#list-product").append(`
                  <div class="col-lg-3 col-md-6 col-sm-6">
                      <div class="product__item">
                          <div class="product__item__pic set-bg" data-setbg="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg">
                              <img src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg" alt="${item.name}">
                              <ul class="product__item__pic__hover">
                                  <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                  <li><a href="product/${item.id}"><i class="fa fa-eye"></i></a></li>
                                  <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                              </ul>
                          </div>
                          <div class="product__item__text">
                              <h6><a href="#">${item.name}</a></h6>
                              <h5>$${item.price}</h5>
                          </div>
                      </div>
                  </div>
                `);
              }
            });
          }
        });
    } else {
      fetch("/api/v1/product")
        .then((response) => response.json())
        .then((response) => {
          if (response.success) {
            response.data.forEach((item) => {
              if (item.active == 1) {
                $("#list-product").append(`
                  <div class="col-lg-3 col-md-6 col-sm-6">
                      <div class="product__item">
                          <div class="product__item__pic set-bg" data-setbg="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg">
                              <img src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg" alt="${item.name}">
                              <ul class="product__item__pic__hover">
                                  <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                  <li><a href="product/${item.id}"><i class="fa fa-eye"></i></a></li>
                                  <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                              </ul>
                          </div>
                          <div class="product__item__text">
                              <h6><a href="#">${item.name}</a></h6>
                              <h5>$${item.price}</h5>
                          </div>
                      </div>
                  </div>
                `);
              }
            });
          }
        });
    }
  });
}

const updateCartCount = () => {
  const userCart = JSON.parse(localStorage.getItem("user-cart")) || [];
  const cartCount = userCart.reduce((total, item) => total + item.quantity, 0);
  // const cartCount = userCart.length;
  // console.log(cartCount);
  $("#cart-count").text(cartCount);
};

$(document).ready(function () {
  menuCategory();
  sortByPrice();
  updateCartCount();
});
