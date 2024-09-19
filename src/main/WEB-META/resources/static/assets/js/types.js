/**
 * @typedef {'create'|'update'|'delete'} SetModalType
 */
/**
 * @typedef {Object} ErrorResponse - Response from server
 * @property {string} [name] - Error's name
 * @property {string} [root] - Error's root cause (JAVA exception)
 * @property {string} [defaultMessage] - Most of the time will be validators translated message! But could be custom message
 * @property {string} [rejectedValue] - If validation fail, specified rejected value (mostly unused).
 * @property {string} [field] - Indicating the field whose validation fail
 */
/**
 * @typedef {Object} ErrorObject 
 * @property {boolean} [consumed] - Indicate if the Error Object is already proccess by formPrefix mechanism (SUCCESSFULLY)
 * @property {Response} response - Fetch Api Response
 * @property {ErrorResponse[]} [errors] - List of Error Response
 * @property {string} [text] - Text value of response in case of non-json object.
 */
/**
 * @typedef {'error'|'warn'|'info'|'success'} ToastType
 * @default 'success'
 */
/**
 * @typedef {string} FormPrefix - prefix for form fields, eg: input is "createUser__username", formPrefix should be "createUser__"
 */
/**
 * @typedef {Object} DonationState
 * @property {string} value - Donation state value
 * @property {DonationStateLocalized} display - Donation state display
 * @description 
 *  "<value>":{
 *    "vn":{"<translatedValue>"}
 *  }
 */
/**
 * @typedef {Object} DonationStateLocalized
 * @property {string} vn - Vietnamize Donation state label
 */
/**
 * @typedef {string|number|HTMLFormElement} IdOrForm - id's value
 *  or form element which contains input with name id
 */
/**
 * @typedef {Object} UserState
 * @property {string} value - User state value
 * @property {UserStateLocalized} display - User state display
 * @description 
 *  "<value>":{
 *    "vn":{"<translatedValue>"}
 *  }
 */

/**
 * @typedef {Object} UserStateLocalized
 * @property {string} vn - Vietnamize User state label
 */
/**
 * @typedef {Object} UserGetResponse
 * @property {number} id
 * @property {string} username
 * @property {string} firstName
 * @property {string} lastName
 * @property {string} fullName
 * @property {string} email
 * @property {string} phone
 * @property {'ACTIVE'|'INACTIVE'} state - User state VALUE
 * @property {string} role - User role VALUE
 */
/**
 * @typedef {Object} UserCreateRequest
 * @property {string} firstName
 * @property {string} lastName
 * @property {string} email
 * @property {string} phone
 * @property {string} username
 * @property {string} password
 * 
 */
/**
 * @typedef {Object} UserUpdateRequest
 * @property {string} firstName
 * @property {string} lastName
 * @property {string} email
 * @property {string} phone
 * @property {string} username
 * @property {string} role - User role VALUE
 * @property {string} state - User state VALUE
 * 
 */
/**
 * @typedef {Object} DonationCreateRequest
 * @property {number} [id] - exist on update only! Server do tolerate!
 * @property {string} code
 * @property {string} name
 * @property {Date} startDate
 * @property {Date} endDate
 * @property {string} organizationName
 * @property {number} phone
 * @property {string} message
 */
/**
 * @typedef {Object} DonationGetResponse
 * @property {number} id
 * @property {string} code
 * @property {string} name
 * @property {Date} startDate
 * @property {Date} endDate
 * @property {string} organizationName
 * @property {number} phone
 * @property {string} description
 * @property {'NEW'|'INPROCESS'|'CLOSED'|'END'} state
 * @property {string} totalAmount - formatted in VND
 */
/**
 * @typedef {Object} BootstrapModal
 * @property {function ():void} show - Show modal
 * @property {function ():void} hide - Hide modal
 */
/**
 * @typedef {Object} WSMessage
 * @property {string} message
 * @property {string} entityID
 * @property {string} entityName
 * @property {string} redirectURL
 */