function loadProduct() {
  $("#list-product").empty();
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
                            <a href="/product/${item.id}">
                                <img src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg" alt="${item.name}">
                            </a>
                            <ul class="product__item__pic__hover">
                                <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                <li><a href="/product/${item.id}"><i class="fa fa-eye"></i></a></li>
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

function searchProductByColor() {
  $("#menu-color").on("click", ".btn-search-color", function () {
    let color = $(this).data("search_color");
    $("#list-product").empty();

    fetch("/api/v1/product/find-by-color/" + color)
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
  });
}

function menuColor() {
  fetch("/api/v1/product")
    .then((response) => response.json())
    .then((response) => {
      if (response.success) {
        const uniqueColors = []; // Khởi tạo mảng tạm thời để lưu trữ các giá trị màu sắc duy nhất
        response.data.forEach((item) => {
          if (item.active === 1 && !uniqueColors.includes(item.color)) {
            // Kiểm tra xem màu sắc đã xuất hiện trước đó chưa
            uniqueColors.push(item.color); // Nếu chưa, thêm màu sắc vào mảng
            $("#menu-color").append(`
                            <div class="sidebar__item__size btn-search-color" type="button" data-search_color="${item.color}">
                                <label for="${item.color}">${item.color}
                                <input type="radio" id="${item.color}" name="color">
                                </label>
                            </div>
                        `);
          }
        });
        searchProductByColor();
      }
    });
}

function searchProductByTrademark() {
  $("#menu-trademark").on("click", ".btn-search-trademark", function () {
    let trademark = $(this).data("search_trademark");
    $("#list-product").empty();

    fetch("/api/v1/product/find-by-trademark/" + trademark)
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
  });
}

function menuTrademark() {
  fetch("/api/v1/product")
    .then((response) => response.json())
    .then((response) => {
      if (response.success) {
        const uniqueTrademarks = [];
        response.data.forEach((item) => {
          if (item.active === 1 && !uniqueTrademarks.includes(item.trademark)) {
            uniqueTrademarks.push(item.trademark);
            $("#menu-trademark").append(`
                            <div class="sidebar__item__size btn-search-trademark" type="button" data-search_trademark="${item.trademark}">
                                <label for="${item.trademark}">${item.trademark}
                                <input type="radio" id="${item.trademark}" name="trademark">
                                </label>
                            </div>
                        `);
          }
        });
        searchProductByTrademark();
      }
    });
}

$(document).ready(function () {
  loadProduct();
  menuColor();
  menuTrademark();
});
