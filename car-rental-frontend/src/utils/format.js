/**
 * Formats a number as a currency string.
 * @param {number} val - The number to format.
 * @returns {string} The formatted currency string.
 */
export function formatRate(val) {
    if (val == null) return '';
    return new Intl.NumberFormat('en-ZA', { style: 'currency', currency: 'ZAR' }).format(val);
}

/**
 * Formats a Date object into a readable date string.
 * @param {Date} date - The date to format.
 * @returns {string} The formatted date string.
 */
export function formatDate(date) {
    if (!date) return '';
    const d = new Date(date);
    return new Intl.DateTimeFormat('en-ZA', { dateStyle: 'long' }).format(d);

}
