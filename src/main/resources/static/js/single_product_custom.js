$(document).ready(() => {
	$('.btnAddToCart').on('click', (element) => {
		const thisBtn = $(element.currentTarget);
		const masp = thisBtn.data("masanpham");
		const soLuong=$('#soLuong').val();
		$.ajax({
			url: "/shopping-cart/addToCart",
			method: "POST",
			// type: "application/json",
			data: {
				maSanPham: masp,
				soLuong: soLuong
			},
			success: function(response) {
				const obj = JSON.parse(response);
				console.log(obj)
				$('#checkout_items').html(obj.soLuong);
			}
		});
	});

	//Fly to cart
	$('.items').flyto({
    		item: '.product_img', // item cần bay đến giỏ hàng
    		target: '.checkout', // giỏ hàng
    		button: '.btnAddToCart' // button add vào giỏ hàng
    	});
});