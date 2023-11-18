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

});
