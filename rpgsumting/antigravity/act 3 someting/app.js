// --- DATA GENERATOR (Creates 100 realistic mock products) ---
const categories = ['Mice', 'Keyboards', 'Headsets', 'Deskmats'];
const brands = ['Specter', 'Vanguard', 'Nova', 'Pulse', 'Apex'];
let allProducts = [];

for (let i = 1; i <= 100; i++) {
    const cat = categories[Math.floor(Math.random() * categories.length)];
    const brand = brands[Math.floor(Math.random() * brands.length)];
    const price = Math.floor(Math.random() * 8000) + 500;
    const rating = (Math.random() * 1.5 + 3.5).toFixed(1); // Ratings between 3.5 and 5.0
    const stock = Math.floor(Math.random() * 50);
    
    allProducts.push({
        id: i,
        name: `${brand} ${cat} Pro Gen-${Math.floor(Math.random() * 5) + 1}`,
        category: cat,
        price: price,
        rating: rating,
        stock: stock,
        isWishlist: false
    });
}

let currentDisplayData = [...allProducts];
let cart = [];

// --- RENDER ENGINE ---
function renderProducts(productsToRender) {
    const grid = document.getElementById('product-grid');
    document.getElementById('product-count').innerText = productsToRender.length;
    grid.innerHTML = '';

    productsToRender.forEach(product => {
        const stockText = product.stock > 10 ? 'In Stock' : (product.stock > 0 ? `Only ${product.stock} left!` : 'Out of Stock');
        const stockClass = product.stock <= 10 ? 'low' : '';
        const heartColor = product.isWishlist ? 'active' : '';
        
        // Generate Stars based on rating
        let stars = '';
        for(let i=1; i<=5; i++) { stars += (i <= Math.round(product.rating)) ? '★' : '☆'; }

        grid.innerHTML += `
            <div class="product-card">
                <button class="wishlist-btn ${heartColor}" onclick="toggleWishlist(${product.id})">♥</button>
                <div class="product-img">[ ${product.category} Image ]</div>
                <h3 class="product-title">${product.name}</h3>
                <div class="product-rating">${stars} (${product.rating})</div>
                <p class="product-price">₱${product.price.toLocaleString()}</p>
                <p class="product-stock ${stockClass}">${stockText}</p>
                <button class="btn-outline" ${product.stock === 0 ? 'disabled style="opacity:0.5"' : ''} onclick="addToCart('${product.name}', ${product.price})">
                    ${product.stock === 0 ? 'Out of Stock' : 'Add to Cart'}
                </button>
            </div>
        `;
    });
}

// --- INTERACTIVITY & FEATURES ---
function sortProducts() {
    const sortType = document.getElementById('sort-select').value;
    if (sortType === 'price-low') currentDisplayData.sort((a, b) => a.price - b.price);
    else if (sortType === 'price-high') currentDisplayData.sort((a, b) => b.price - a.price);
    else if (sortType === 'rating') currentDisplayData.sort((a, b) => b.rating - a.rating);
    else currentDisplayData.sort((a, b) => a.id - b.id); // default
    renderProducts(currentDisplayData);
}

function filterCategory(cat) {
    currentDisplayData = allProducts.filter(p => p.category === cat);
    renderProducts(currentDisplayData);
}

function filterWishlist() {
    currentDisplayData = allProducts.filter(p => p.isWishlist === true);
    renderProducts(currentDisplayData);
    if(currentDisplayData.length === 0) showToast('Your wishlist is empty.', 'var(--warning-color)');
}

function toggleWishlist(id) {
    const product = allProducts.find(p => p.id === id);
    product.isWishlist = !product.isWishlist;
    renderProducts(currentDisplayData); // Re-render to update heart color
    
    if(product.isWishlist) showToast(`Added ${product.name} to Wishlist!`, 'var(--danger-color)');
}

function addToCart(name, price) {
    cart.push({ name, price });
    document.getElementById('cart-count').innerText = cart.length;
    updateCartUI();
    showToast(`Added ${name} to Cart`, 'var(--success-color)');
}

function showToast(message, color) {
    const container = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.style.borderLeftColor = color;
    toast.innerHTML = `<strong>Notice:</strong> ${message}`;
    container.appendChild(toast);
    
    // Remove from DOM after animation
    setTimeout(() => { toast.remove(); }, 3000);
}

// --- CART LOGIC ---
function updateCartUI() {
    const container = document.getElementById('cart-items');
    if (cart.length === 0) {
        container.innerHTML = '<p style="color: gray; text-align: center;">Your cart is empty.</p>';
        document.getElementById('cart-total-price').innerText = '₱0';
        return;
    }
    container.innerHTML = '';
    let total = 0;
    cart.forEach(item => {
        total += item.price;
        container.innerHTML += `
            <div style="display: flex; justify-content: space-between; margin-bottom: 1rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.5rem;">
                <span style="font-size: 0.9rem;">${item.name}</span>
                <span style="color: var(--accent-color); font-weight: bold;">₱${item.price.toLocaleString()}</span>
            </div>
        `;
    });
    document.getElementById('cart-total-price').innerText = '₱' + total.toLocaleString();
}

function openCart() {
    document.getElementById('cart-sidebar').classList.add('active');
    document.getElementById('overlay').classList.add('active');
    updateCartUI();
}

function closeCart() {
    document.getElementById('cart-sidebar').classList.remove('active');
    document.getElementById('overlay').classList.remove('active');
}

// Initialize application on load
window.onload = () => {
    renderProducts(allProducts);
    updateCartUI();
};