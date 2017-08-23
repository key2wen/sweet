var Login = function () {



    return {
        //main function to initiate the module
        init: function (options) {
            this.redirect = options.redirect;
            handleLogin();
        }

    };

}();
