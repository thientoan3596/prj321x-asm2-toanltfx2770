/**
 * @param {Response} response 
 * @throws {ErrorObject}
 */
async function nonJsonErrorHandler(response) {
  const data = await response.text();
  if (data.toLowerCase().startsWith("<!doctype html>")) {
    toast("DEV ERROR! Receiving HTML instead of JSON.", "error");
    console.error("List to check!\n1. URL\n2. Body Content");
    console.error("Sent Body");
    console.log(data);
  }
  const err = new Error(`HTTP error! Status: ${response.status}`);
  err.response = response;
  err.text = data;
  throw err;
}
/**
* General handling fetch api response error!
* NB! DO RE-THROW! 
* @param {ErrorObject} error 
*/
function errorHandler(error) {
  if (!error.consumed) {
    console.log(error);

    toast(error.response?.status ?? 'Error!', "error");
    if (error.errors) {
      error.errors.forEach(err => {
        if (err.defaultMessage) toast(err.defaultMessage, "error");
        console.error(err.name);
      })
    }
    else if (error.text)
      console.error(error.text);
    error.consumed = true;
  }
  throw error;
}
/**
* consume error object for post + get method
* who does not require from validation (delete for example).
* @param {ErrorObject} error 
*/
function errorConsumer(error) {
  if (error.errors && error.errors.length > 0) {
    if (error.errors[0].defaultMessage)
      toast(error.errors[0].defaultMessage, "error");
    else if (error.errors[0].name)
      toast(error.errors[0].name, "error");
    else {
      toast("Lỗi " + (error.response?.status ?? " không xác định ") + "!", "error");
      console.error(error);
    }
  } else if (error.text) {
    toast("Lỗi " + (error.response?.status ?? " không xác định ") + "!", "error");
    console.error(error.text);
  } else {
    toast("Lỗi " + (error.response?.status ?? " không xác định ") + "!", "error");
    console.error(error);
  }
}
/**
* Just making sure the error is consumed by formPrefix mechanism.
* If not, manually consume it.
* @param {ErrorObject} error
* @param {FormPrefix} formPrefix
*/
function errorConsumerFormValidation(error, formPrefix) {
  if (!formPrefix) {
    if (error.defaultMessage)
      toast(error.defaultMessage, "error");
    else if (error.errors) {
      const msg = error.errors[0].defaultMessage ?? error.errors[0].message ?? error.errors[0].name;
      toast(msg, "error");
    }
    else if (error.response)
      toast(error.response.status, "error");
  }
  if (!error.consumed)
    toast("Lỗi!", "error");
  console.error(error);
}