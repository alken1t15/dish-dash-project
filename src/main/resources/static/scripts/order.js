$(document).ready(function() {

    let solo = true;

    function setAddressBlocks(contactBlocks ,status = 0){
        
        contactBlocks.map((index, el)=>{

            if(status === 1){
                el.classList.remove("address-item-none");
            }
            
            else{
                el.classList.add("address-item-none");
            }

        })
    }
    
    function setState (element, otherElements) {

        let contactBlocks = $('.address-item-other');
        
        otherElements.map((index, el)=>{
            
            el.classList.remove("order-type-active");
            setAddressBlocks(contactBlocks, 0);
            
            if(el.dataset.index === element.target.dataset.index){
                element.target.classList.add("order-type-active");
                setAddressBlocks(contactBlocks, 1);
            }
        })
    }


    $('.order-type').on('click', function (e) {
        let current = e;
        let elements = $('.order-type');
        setState(current, elements);
        
    });

    $('.address-item-full').on('click', function () {
        $('.slider-food').slick('slickPrev');
    });

    $('.custom-arrows-food button.next-sec').on('click', function () {
        $('.slider-food').slick('slickNext');
    });



    function calculateTotal() {
        let total = 0;
        const cartItems = document.querySelectorAll('.cart-item');

        cartItems.forEach(item => {
            const priceElement = item.querySelector('.price-text-number');
            const quantityElement = item.querySelector('.quantity .number');
            const price = parseFloat(priceElement.textContent);
            const quantity = parseInt(quantityElement.textContent);

            total += price * quantity;
        });

        totalAmountElement.textContent = total;
    }


    calculateTotal();

});