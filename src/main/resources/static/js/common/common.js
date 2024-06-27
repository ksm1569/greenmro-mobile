function apiCall(apiUrl, callback) {
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => callback(data))
        .catch(error => console.error('Error fetching product details:', error));
}

/**
 * Fetches product details from the server and executes a callback function with the response data.
 * @param {*[][]} prefItem - The product ID to fetch details for.
 * @param {function} callback - A callback function to execute with the fetched product data.
 */
function fetchProductDetails(prefItem, callback) {
    const baseUrl = window.location.origin;
    const apiUrl = `${baseUrl}/api/products/${prefItem}`;

    apiCall(apiUrl, callback);
}

/**
 * Fetches product details from the server and executes a callback function with the response data.
 * @param {*[][]} prefItem - The product ID to fetch details for.
 * @param {function} callback - A callback function to execute with the fetched product data.
 */
function fetchProductCategory(prefItem, callback) {
    const baseUrl = window.location.origin;
    const apiUrl = `${baseUrl}/api/products/${prefItem}/categories`;

    apiCall(apiUrl, callback);
}