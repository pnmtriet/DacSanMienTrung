$(document).ready(() => {
    //Id
	$(".inputProductId").change(function(element){
	 var max = parseInt($(this).attr('max'));
	 var min = parseInt($(this).attr('min'));
	 if ($(this).val() > max){
	    $(this).val(max);
	 }else if ($(this).val() < min){
	    $(this).val(min);
	 }

	 //Price
	 $(".inputProductPrice").change(function(element){
     	 var max = parseInt($(this).attr('max'));
     	 var min = parseInt($(this).attr('min'));
     	 if ($(this).val() > max){
     	    $(this).val(max);
     	 }else if ($(this).val() < min){
     	    $(this).val(min);
     	 }

     //Discount
     $(".inputProductDiscount").change(function(element){
          	 var max = parseInt($(this).attr('max'));
          	 var min = parseInt($(this).attr('min'));
          	 if ($(this).val() > max){
          	    $(this).val(max);
          	 }else if ($(this).val() < min){
          	    $(this).val(min);
          	 }
});