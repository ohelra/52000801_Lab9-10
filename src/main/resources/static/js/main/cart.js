const getProduct = async (productId) => {
  try {
    const response = await $.ajax({
      url: `/api/v1/product/${productId}`,
      method: "GET",
    });
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

const renderCartItem = async (item) => {
  const product = await getProduct(item.productId);
  const total = item.quantity * product.price;
  const html = `
    <tr>
      <td class="shoping__cart__item">
        <a href="/product/${item.productId}">
          <img
            src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg"
            width="100px"
            height="100px"
            alt=""
          />
          <h5>${product.name}</h5>
        </a>
      </td>
      <td class="shoping__cart__price">$${product.price}</td>
      <td class="shoping__cart__quantity">
        <div class="quantity">
          <div class="cart__item__quantity pro-qty">
            <input type="text" value="${item.quantity}" />
          </div>
        </div>
      </td>
      <td class="shoping__cart__total">$${total}</td>
      <td class="shoping__cart__item__close">
        <span class="icon_close"></span>
      </td>
    </tr>
  `;
  $("#shoping__cart__checkout__body").append(html);
};

const loadUserCart = async () => {
  const userCart = JSON.parse(localStorage.getItem("user-cart")) || [];
  if (userCart.length > 0) {
    try {
      for (const item of userCart) {
        await renderCartItem(item);
      }
      handleItemQuantity();
      handleDeleteCartItem();
      updateOrderTotal();
    } catch (error) {
      console.error(error);
    }
  } else {
    const html = `
      <tr>
        <td colspan="4">
          Your shopping cart is empty. Buy something <a class="link-home" href="/">here.</a>
        </td>
      </tr>
    `;
    $("#shoping__cart__checkout__body").append(html);
  }
};

/** Xử lý số lượng hàng */
const handleItemQuantity = () => {
  let proQty = $(".cart__item__quantity");
  proQty.prepend(
    `<button class="dec qtybtn" style="border:none; background:inherit">-</button>`
  );
  proQty.append(
    `<button class="inc qtybtn" style="border:none; background:inherit">+</button>`
  );
  proQty.on("click", ".qtybtn", function () {
    let $button = $(this);
    let oldValue = parseInt($button.parent().find("input").val());
    var newVal = $button.hasClass("inc")
      ? oldValue < 5
        ? parseFloat(oldValue) + 1
        : oldValue
      : oldValue > 1
      ? parseFloat(oldValue) - 1
      : 1;

    $button.parent().find("input").val(newVal);
    let price = parseFloat(
      $button
        .parents(".shoping__cart__quantity")
        .prev(".shoping__cart__price")
        .text()
        .slice(1)
    );
    $button
      .parents(".shoping__cart__quantity")
      .next(".shoping__cart__total")
      .text("$" + newVal * price);

    const index = $(this).closest("tr").index();
    let userCart = JSON.parse(localStorage.getItem("user-cart")) || [];

    if (index !== -1) {
      userCart[index].quantity = newVal;
      localStorage.setItem("user-cart", JSON.stringify(userCart));
    }

    updateOrderTotal();
  });
};

/** Xử lý xóa sản phẩm ra khỏi cart */
const handleDeleteCartItem = () => {
  $("#shoping__cart__checkout__body").on(
    "click",
    ".shoping__cart__item__close",
    function () {
      const index = $(this).closest("tr").index();
      console.log(index);
      let userCart = JSON.parse(localStorage.getItem("user-cart")) || [];
      userCart.splice(index, 1);
      localStorage.setItem("user-cart", JSON.stringify(userCart));
      $(this).closest("tr").remove();
      updateOrderTotal();
    }
  );
};

const updateOrderTotal = async (index, quantity) => {
  let totals = $(".shoping__cart__total");
  let orderSubTotal = 0;

  totals.each(function () {
    let item_total = $(this).text().slice(1);
    orderSubTotal += parseFloat(item_total);
  });

  let orderTotal = parseFloat(
    (orderSubTotal + orderSubTotal * 0.08).toFixed(1)
  );

  $("#order-sub-total").text(orderSubTotal);
  $("#order-total").text(orderTotal);
};

const dataValidator = (name, address, city, phone, email, cart) => {
  const validateFields = [
    { field: name, message: "Please enter your name." },
    { field: address, message: "Please enter your address." },
    { field: city, message: "Please enter your city." },
    { field: phone, message: "Please enter your phone number." },
    { field: cart, message: "Please add something to your cart." },
    {
      field: email,
      message: "Please enter your email address.",
      pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i,
      invalidMessage: "Please enter a valid email address.",
    },
  ];

  let isValid = true;

  for (let i = 0; i < validateFields.length; i++) {
    const field = validateFields[i].field;
    const message = validateFields[i].message;
    const pattern = validateFields[i].pattern;
    const invalidMessage = validateFields[i].invalidMessage;

    if (field === "") {
      alert(message);
      isValid = false;
      break;
    } else if (pattern && !pattern.test(field)) {
      alert(invalidMessage);
      isValid = false;
      break;
    }
  }
  return isValid;
};

/** Xử lý thanh toán */
const proceedPayment = () => {
  $("#order-form").submit(function (event) {
    event.preventDefault(); // Ngăn chặn form submit mặc định

    // Lấy dữ liệu từ form
    /** Toàn bộ dữ liệu điều ở dạng string */
    let name = $('input[name="name"]').val();
    let address = $('input[name="address"]').val();
    let city = $('input[name="city"]').val();
    let phone = $('input[name="phone"]').val();
    let email = $('input[name="email"]').val();
    let note = $('input[name="note"]').val();
    let cart = localStorage.getItem("user-cart").toString();
    let billId = crypto.randomUUID();

    if (dataValidator(name, address, city, phone, email, cart)) {
      const order = {
        name,
        billId,
        address,
        city,
        phone,
        email,
        note,
        cart,
        orderDate: new Date().toISOString(),
      };
      console.log(order);

      $.ajax({
        url: "/api/v1/order",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(order),
        success: function (response) {
          let billId = response.data.billId;

          if (
            confirm(
              `Your order will be delivered within 7 days.\nYour bill code is: ${billId}.\nIf you have any questions, please contact the hotline: 1900 0000.`
            )
          ) {
            localStorage.setItem("user-cart", JSON.stringify([]));
            setTimeout(() => {
              window.location.href =
                window.location.origin + `/order?billId=${billId}`;
            }, 1000);
          }
        },
        error: (error) => {
          console.log(error);
          alert("Failed to submit order. Please try again later.");
        },
      });
    }
  });
};

$(document).ready(() => {
  loadUserCart();
  proceedPayment();
});
