$(document).ready(function() {
    $('.slider-food').slick({
        dots: false,
        arrows: false,
        infinite: true,
        speed: 300,
        slidesToShow: 4,
        slidesToScroll: 1
    });

    $('.custom-arrows-food button.prev-sec').on('click', function () {
        $('.slider-food').slick('slickPrev');
    });

    $('.custom-arrows-food button.next-sec').on('click', function () {
        $('.slider-food').slick('slickNext');
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
