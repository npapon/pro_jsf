
var loginInput = document.getElementById("login");
var erreurLoginDiv = document.getElementById("erreurlogin");
var motdepasseInput = document.getElementById("motdepasse");
var erreurMotdepasseDiv = document.getElementById("erreurmotdepasse");
var loginValide = false;
var motdepasseValide = false;



loginInput.addEventListener("blur", function() {
	validationLogin();

})

function validationLogin() {
	if (loginInput.value.trim().length <= 3) {

		erreurLoginDiv.innerHTML = "Le login doit faire plus de 3 caractères";
		loginValide = false;

	} else {
		loginValide = true;
		erreurLoginDiv.innerHTML = "";

	}
}

motdepasseInput.addEventListener("blur", function() {
	validationMotdepasse();
})

function validationMotdepasse() {
	if (motdepasseInput.value.trim().length <= 3) {

		erreurMotdepasseDiv.innerHTML = "Le mot de passe doit faire plus de 3 caractères";
		confirmationValide = false;
	} else {
		motdepasseValide = true;
		erreurMotdepasseDiv.innerHTML = "";

	}
	
}



submitInput.addEventListener("click", function(e) {

	validationFormulaire(e);
})

function validationFormulaire(e) {
	validationLogin();
	validationMotdepasse();
	if (loginValide == false || motdepasseValide == false) {
		e.preventDefault();
	}
}

