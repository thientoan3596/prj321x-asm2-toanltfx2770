// const applyJobModalElement = document.querySelector("#applyJobModal");
// const applyJobModal = new bootstrap.Modal(applyJobModalElement);
// const newCVFile = document.querySelector("#applyJobModal__cv_file");
// const submitBtn = document.querySelector("#applyJobModal__submit");
// const cvSelect = document.querySelector("#applyJobModal__cv_cvId");
// const applyFormElement = document.querySelector("#applyForm");

/* async function toggleApplyModal() {
  try {
    if (cvSelect.options.length == 1) {
      const response = await fetch('/api/cv-list', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
        },
      });
      const data = await response.json();
      data.forEach(cv => {
        cvSelect.add(new Option(cv.fileName, cv.id, cv.isDefault, cv.isDefault));
      });
    }

    applyJobModal.show();
  } catch (error) {
    alert(error)
  }
}
async function applyJob(evt) {
  evt.preventDefault();
  const jobId = window.location.href.split('/').pop();
  const formData = new FormData(applyFormElement);
  try {
    const response = await fetch('/api/apply-job/' + jobId, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
      },
      body: formData
    });
    const data = await response.json();
    if (response.status == 400) {
      data.forEach(err => {
        if (err.field) {
          const inputField = document.querySelector('#applyJobModal__cv_' + err.field);
          inputField.classList.add('is-invalid');
          const inputFieldFeedback = inputField.nextElementSibling;
          inputFieldFeedback.classList.remove('d-none');
          inputFieldFeedback.innerText = err.defaultMessage;
        } else {
          console.error(err);
        }
      })

    } if (response.status == 201) {
      alert("Created")
    }
    else {
      console.log(data);
    }
  } catch (error) {
    console.log(error);
  }
} */
/* document.querySelector("#applyJobModal__cv_cvId").addEventListener('change', (e) => {
  if (e.target.value == -1) {
    document.querySelector("#applyJobModal__cv_file").parentElement.classList.remove('d-none');
    if (newCVFile.value == '')
      submitBtn.disabled = true;
  } else {
    document.querySelector("#applyJobModal__cv_file").parentElement.classList.add('d-none');
    submitBtn.disabled = false;
  }
});
document.querySelector("#applyJobModal__cv_file").addEventListener("change", (e) => {
  if (e.target.value != '')
    submitBtn.disabled = false;
  else
    submitBtn.disabled = true;
});

window.addEventListener('DOMContentLoaded', () => {
  if (cvSelect.value == -1) {
    console.log("here");
    document.querySelector("#applyJobModal__cv_file").parentElement.classList.remove('d-none');
  }
  if (applyJobModalElement.getAttribute('data-default-shown') == 'true') {
    applyJobModal.show();
  }
}); */