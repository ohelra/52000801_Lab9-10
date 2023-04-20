const getBill = async (billId) => {
  try {
    const response = await $.ajax({
      url: `/api/v1/order/${billId}`,
      method: "GET",
    });
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

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

const renderOrderItem = async (item) => {
  const product = await getProduct(item.productId);
  const total = item.quantity * product.price;
  const html = `
      <tr>
        <td class="shoping__cart__item">
          <img
            src="https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn/Products/Images/42/289700/iphone-14-pro-max-vang-thumb-600x600.jpg"
            width="100px"
            height="100px"
            alt=""
          />
          <a href="/product/${item.productId}"><h5>${product.name}</h5></a>
        </td>
        <td class="shoping__cart__price">$${product.price}</td>
        <td class="shoping__cart__quantity">
          <div class="quantity">
            <div class="cart__item__quantity pro-qty">
              <input type="text" value="${item.quantity}" disabled />
            </div>
          </div>
        </td>
        <td class="shoping__order__total" style="font-weight:700;font-size:18px;color:1c1c1c">$${total}</td>
        
      </tr>
    `;
  $("#shoping__order__checkout__body").append(html);
};

const loadUserBill = async () => {
  const billId = $("#bill-id").text();

  if (!billId) {
    const html = `
      <tr>
        <td colspan="4">
          Bill not exist.
        </td>
      </tr>
    `;
    return $("#shoping__order__checkout__body").empty().append(html);
  }
  const bill = await getBill(billId);
  if (bill) {
    let cartData = JSON.parse(bill.cart);
    // console.log(cartData);

    if (cartData.length > 0) {
      try {
        for (const item of cartData) {
          await renderOrderItem(item);
        }
        updateOrderTotal();

        return;
      } catch (error) {
        console.error(error);
      }
    }
  } else {
    const html = `
      <tr>
        <td colspan="4">
          Bill not exist.
        </td>
      </tr>
    `;
    return $("#shoping__order__checkout__body").empty().append(html);
  }

  //
};

const updateOrderTotal = async (index, quantity) => {
  let totals = $(".shoping__order__total");
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

const copyBillId = () => {
  let billId = $("#bill-id").text();
  if (!billId) {
    $("#copy-btn").hide();
    $("#note-save-bill-code").hide();
  }
  $("#copy-btn").on("click", function () {
    if (billId) {
      navigator.clipboard
        .writeText(billId)
        .then(() => {
          $("#copy-btn").attr("title", "copied");
          setTimeout(() => {
            $("#copy-btn").attr("title", "copy");
          }, 5000);
        })
        .catch((err) => {
          console.error("Error: ", err);
        });
    }
  });
};

$(document).ready(() => {
  loadUserBill();
  copyBillId();
});
