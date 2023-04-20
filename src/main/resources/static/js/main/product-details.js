const loadProductDetails = async () => {
  await fetch("/api/v1" + window.location.pathname)
    .then((response) => response.json())
    .then((response) => {
      if (response.success) {
        $("#product-details").append(`
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-6 col-md-6">
                                <div class="product__details__pic">
                                    <div class="product__details__pic__item">
                                        <img class="product__details__pic__item--large"
                                            src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg">
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6">
                                <div class="product__details__text">
                                    <h3>${response.data.name}</h3>
                                    <div class="product__details__price">$${response.data.price}</div>
                                    <p>${response.data.description} - Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a. Vestibulum ac diam sit amet quam
                                        vehicula elementum sed sit amet dui. Sed porttitor lectus nibh. Vestibulum ac diam sit amet
                                        quam vehicula elementum sed sit amet dui. Proin eget tortor risus.</p>
                                    <div class="sidebar__item__size btn-search-color" type="button"
                                        data-search_color="${response.data.color}">
                                        <label for="${response.data.color}">${response.data.color}
                                            <input type="radio" id="${response.data.color}" name="color">
                                        </label>
                                    </div>
                                    <div>
                                        <div class="product__details__quantity">
                                            <div class="quantity">
                                                <div class="detail__item__quantity pro-qty">
                                                    <input id="item-quantity" type="text" value="1">
                                                </div>
                                            </div>
                                        </div>
                                        <a id="btn-add-to-cart" class="primary-btn text-white pointer" data-product_id="${response.data.id}">ADD TO CARD</a>
                                        <a href="#" class="heart-icon"><span class="icon_heart_alt"></span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `);
      }
    });
};

// cài đặt chức năng add to cart
const addToCart = () => {
  $("#btn-add-to-cart").click(async () => {
    let product = await fetch("/api/v1" + window.location.pathname)
      .then((response) => response.json())
      .then((response) => {
        return response.data;
      });

    let quantity = parseInt($("#item-quantity").val());
    let userCart = JSON.parse(localStorage.getItem("user-cart")) || [];
    const productId = $("#btn-add-to-cart").data("product_id");
    let success = false;

    const index = userCart.findIndex((item) => item.productId === productId);
    if (index !== -1) {
      if (userCart[index].quantity + quantity <= 5) {
        userCart[index].quantity += quantity;
        success = true;
      } else {
        alert(
          `Quantity of ${product.name} in your cart is reaching limit (5 products).`
        );
      }
    } else {
      userCart.push({
        productId,
        quantity,
      });
      success = true;
    }

    if (success) {
      alert(`You have added ${quantity} ${product.name} to cart.`);
      localStorage.setItem("user-cart", JSON.stringify(userCart));
      window.location.reload();
    }
  });
};

$(document).ready(async () => {
  loadProductDetails()
    .then(() => {
      let proQty = $(".detail__item__quantity");
      proQty.prepend(
        `<button class="dec qtybtn" style="border:none; background:inherit">-</button>`
      );
      proQty.append(
        `<button class="inc qtybtn" style="border:none; background:inherit">+</button>`
      );
      proQty.on("click", ".qtybtn", function () {
        var $button = $(this);
        var oldValue = $button.parent().find("input").val();
        var newVal = $button.hasClass("inc")
          ? oldValue < 5
            ? parseFloat(oldValue) + 1
            : oldValue
          : oldValue > 1
          ? parseFloat(oldValue) - 1
          : 1;
        $button.parent().find("input").val(newVal);
      });
    })
    .then(() => addToCart());
});
