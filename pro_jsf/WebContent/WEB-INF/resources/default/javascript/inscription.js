
var loginInput = document.getElementById("login");
var erreurLoginDiv = document.getElementById("erreurlogin");
var nomInput = document.getElementById("nom");
var erreurNomDiv = document.getElementById("erreurnom");
var motdepasseInput = document.getElementById("motdepasse");
var erreurMotdepasseDiv = document.getElementById("erreurmotdepasse");
var confirmationInput = document.getElementById("confirmation");
var erreurConfirmationDiv = document.getElementById("erreurconfirmation");
var emailInput = document.getElementById("email");
var erreurEmailDiv = document.getElementById("erreuremail");
var submitInput = document.getElementById("submit");
var loginValide = false;
var nomValide = false;
var motdepasseValide = false;
var confirmationValide = false;
var mailValide = false;


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

nomInput.addEventListener("blur", function() {
	validationNom();

})

function validationNom() {
	if (nomInput.value.trim().length <= 3) {

		erreurNomDiv.innerHTML = "Le nom doit faire plus de 3 caractères";
		nomValide = false;

	} else {
		nomValide = true;
		erreurNomDiv.innerHTML = "";

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
	validationConfirmation();
}

confirmationInput.addEventListener("blur", function() {
	validationConfirmation();
})

function validationConfirmation() {
	if (confirmationInput.value.trim().length <= 3) {

		erreurConfirmationDiv.innerHTML = "Le mot de passe doit faire plus de 3 caractères";
		confirmationValide = false;
	} else {
		if (confirmationInput.value.trim() != motdepasseInput.value.trim()) {
			erreurConfirmationDiv.innerHTML = "Le mot de passe confirmé est différent";
			confirmationValide = false;

		} else {
			confirmationValide = true;
			erreurConfirmationDiv.innerHTML = "";
		}

	}
}


emailInput.addEventListener("blur", function() {
	validationEmail();
})

function validationEmail() {
	if (!/^([^.@]+)(\.[^.@]+)*@([^.@]+\.)+([^.@]+)$/.test(emailInput.value) == true) {
		erreurEmailDiv.innerHTML = "Le format de l'email n'est pas valide";
		mailValide = false;
	}
	else{
		mailValide = true;
		erreurEmailDiv.innerHTML = "";
		
	}

}


submitInput.addEventListener("click", function(e) {

	validationFormulaire(e);
})

function validationFormulaire(e) {
	validationLogin();
	validationNom() 
	validationMotdepasse();
	validationConfirmation();
	validationEmail();
	if (loginValide == false || motdepasseValide == false||confirmationValide==false||mailValide==false||nomValide==false ) {
		e.preventDefault();
	}
}

