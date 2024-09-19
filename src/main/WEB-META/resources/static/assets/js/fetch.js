/**
* @param {String} url -
* @param {Object} data -
* @param {FormPrefix} formPrefix 
* @returns {Promise<Object>} - parsed JSON response.
* @throws {ErrorObject}
* @see {@link ErrorObject}
*/
async function post(url, data, formPrefix) {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
    body: JSON.stringify(data)
  })
  if (response.ok) {
    if (response.status == 204)
      return true;
    return await response.json();
  }
  const contentType = response.headers.get('Content-Type');
  if (contentType && contentType.includes('application/json')) {
    const errorList = await response.json();
    let consumed = false;
    if (response.status == 400 && formPrefix) {
      errorList.forEach(err => {
        const inputField = document.querySelector('#' + formPrefix + err.field);
        const inputFieldFeedback = document.querySelector('#' + formPrefix + err.field + "-feedback");
        if (err.field && err.defaultMessage && inputField) {
          inputField.classList.toggle('is-invalid');
          if (inputFieldFeedback)
            inputFieldFeedback.textContent = err.defaultMessage;
          else
            toast(err.defaultMessage, "error");
        }
        consumed = true;
      });
    }
    const err = new Error(`HTTP error! Status: ${response.status}`);
    err.response = response;
    err.errors = errorList;
    err.consumed = consumed;
    throw err;
  } else {
    const text = await response.text();
    if (text.toLowerCase().startsWith("<!doctype html>")) {
      toast("DEV ERROR! Receiving HTML instead of JSON.", "error");
      console.error("Check list!\n1. URL\n2. Body Content");
      console.error("Sent Body");
      console.log(data ?? 'no body sent');
      console.log(url ?? 'no url sent');
    }
    const err = new Error(`HTTP error! Status: ${response.status}`);
    err.response = response;
    err.text = text;
    throw err;
  }
}