$(document).ready(function(){
    $('.slider').slick({
        dots: false,
        arrows: false,
        infinite: true,
        speed: 300,
        slidesToShow: 5,
        slidesToScroll: 1
    });

    $('.custom-arrows button.prev').on('click', function() {
        $('.slider').slick('slickPrev');
    });

    $('.custom-arrows button.next').on('click', function() {
        $('.slider').slick('slickNext');
    });


    $('.slider-second').slick({
        dots: false,
        arrows: false,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        slidesToScroll: 1
    });

    $('.custom-arrows-second button.prev-sec').on('click', function() {
        $('.slider-second').slick('slickPrev');
    });

    $('.custom-arrows-second button.next-sec').on('click', function() {
        $('.slider-second').slick('slickNext');
    });

    document.addEventListener('DOMContentLoaded', function () {
        var kitchenList = document.getElementById('kitchenList');
        var kitchenItems = kitchenList.getElementsByTagName('li');

        for (var i = 0; i < kitchenItems.length; i++) {
            kitchenItems[i].addEventListener('click', function () {
                selectKitchen(this.innerText);
            });
        }
    });

    function selectKitchen(kitchen) {
        hideKitchenList();
    }

    function showKitchenList() {
        document.getElementById("kitchenList").style.display = "block";
    }

    function hideKitchenList() {
        document.getElementById("kitchenList").style.display = "none";
    }

});