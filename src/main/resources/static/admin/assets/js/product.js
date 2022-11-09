$(document).ready(() => {
     function readURL(input) {
         if (input.files && input.files[0]) {
             var reader = new FileReader();
             reader.onload = function (e) {
                 $('#imgProduct').attr('src', e.target.result);
                 $('#imgProduct').removeAttr('hidden');
             }

             reader.readAsDataURL(input.files[0]);
         }
     }

     $("#inputImgProduct").change(function(){
         readURL(this);
     });

          $(".inputImgProductChiTiet").change(function(element){
                        const thisInput = $(element.currentTarget);
                        const maSp = thisInput.data("masanpham");
                        console.log('#imgProduct'+maSp)
                        console.log(thisInput[0]);
                        console.log(thisInput[0].files);
                        if (thisInput[0].files && thisInput[0].files[0]) {
                          var reader = new FileReader();
                          reader.onload = function (e) {
                          console.log("e.target.result:",e.target.result);
                              $('#imgProductChiTiet'+maSp).attr('src', e.target.result);
                          }
                          reader.readAsDataURL(thisInput[0].files[0]);
                        }
          });
});