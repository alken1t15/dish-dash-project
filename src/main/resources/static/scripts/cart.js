const plusButtons = document.querySelectorAll('.cart-item__btn[data-action="plus"]');
const minusButtons = document.querySelectorAll('.cart-item__btn[data-action="minus"]');
const deleteButtons = document.querySelectorAll('.delete');
const cartList = document.querySelector('.cart-list');
const totalAmountElement = document.getElementById('totalAmount');
const emptyMessageElement = document.querySelector('.empty-message');

function handlePlusButtonClick(event) {
    updateQuantity(event.target.parentNode, 1);
}

function handleMinusButtonClick(event) {
    updateQuantity(event.target.parentNode, -1);
}

function handleDeleteButtonClick(event) {
    const cartItem = event.target.closest('.cart-item');
    cartList.removeChild(cartItem);
    calculateTotal();
    checkEmptyCart();
}

function updateQuantity(container, change) {
    const quantityElement = container.querySelector('.quantity .number');
    let quantity = parseInt(quantityElement.innerHTML);

    if (quantity + change >= 0 && quantity + change <= 20) {
        quantity += change;
        quantityElement.textContent = quantity;

        // let response = await fetch('/article/fetch/post/user', {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/json;charset=utf-8'
        //     },
        //     body: JSON.stringify(user)
        // });
        // event.target.closest('.cart-item')
        calculateTotal();
    }

    if (quantity === 0) {
        const cartItem = container.closest('.cart-item');
        cartList.removeChild(cartItem);
        checkEmptyCart();
    }
}

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

function checkEmptyCart() {
    const cartItems = document.querySelectorAll('.cart-item');
    const take = document.querySelector('.cartBtn');
    const total = document.querySelector('.total');
    const col = document.querySelector('.columns');
    if (cartItems.length === 0) {
        emptyMessageElement.style.display = 'block';
        take.style.display = 'none';
        total.style.display = 'none';
        col.style.display = 'none';

    } else {
        emptyMessageElement.style.display = 'none';
    }
}

plusButtons.forEach(button => {
    button.addEventListener('click', handlePlusButtonClick);
});

minusButtons.forEach(button => {
    button.addEventListener('click', handleMinusButtonClick);
});

deleteButtons.forEach(button => {
    button.addEventListener('click', handleDeleteButtonClick);
});

calculateTotal();
checkEmptyCart();