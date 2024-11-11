function share(url) {
  var currentUrl = encodeURIComponent(window.location.href);
  const shareUrl = url + currentUrl;
  console.log(shareUrl);
  window.open(shareUrl, '_blank');
}
/**
 * @param {string} msg - message to be toasted
 * @param {ToastType} [type] - Default: "success"
 * @description
 *  Toast a message, with default type "success".
 *  If type is not specified, the default type is "success"
 *  If type is "error", the time is 10s, for other types, the time is 3s
 */
function toast(msg, type) {
  const className = type === "error" ? "bg-danger" :
    type == "warn" ? "bg-warning" :
      type == "info" ? "bg-info" : "bg-success";
  const t = Toastify({
    text: msg,
    duration: type === "error" ? 10000 : 3000,
    close: true,
    gravity: "top",
    position: "center",
    stopOnFocus: true,
    className,
    style: {
      background: "none",
    },
    onClick: function () {
      t.hideToast();
    }
  });
  t.showToast();
}
window.addEventListener('DOMContentLoaded', () => {
  //#region Flash Toast
  const toastMessage = sessionStorage.getItem('toastMessage');
  const toastType = sessionStorage.getItem('toastType');
  sessionStorage.removeItem('toastType');
  sessionStorage.removeItem('toastMessage');
  if (toastMessage)
    toast(toastMessage, toastType ?? "success");
  //#endregion Flash Toast
  //#region Input Date
  document.querySelectorAll('input[type=date][data-validation=today').forEach(input => {
    input.setAttribute('min', new Date().toISOString().split('T')[0]);
  });
  //#endregion Flash Toast
});