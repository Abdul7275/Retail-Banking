var mainApp = angular.module("mainApp", ['ngRoute']);
mainApp.config(function($routeProvider) {
    $routeProvider
    .when('/home', {
        templateUrl : 'pages/home.html',
        controller: 'application'
    })
    .when('/createCust', {
        templateUrl : 'pages/createC.html',
        controller: 'CreateCustomerController'
    })
    .when('/updateCust', {
        templateUrl : 'pages/updateC.html',
        controller: 'UpdateCustomerController'
    })
    .when('/deleteCust', {
        templateUrl : 'pages/deleteC.html',
        controller: 'DeleteCustomerController'
    })
    .when('/createAcc', {
        templateUrl : 'pages/createA.html',
        controller: 'CreateAccountController'
    })
    .when('/deleteAcc', {
        templateUrl : 'pages/deleteA.html',
        controller: 'DeleteAccountController'
    })
    .when('/viewCust', {
        templateUrl : 'pages/viewC.html',
        controller: 'ViewCustomer'
    })
    .when('/viewAcc', {
        templateUrl : 'pages/viewA.html',
        controller: 'ViewAccount'
    })
    .otherwise({
      redirectTo: '/home',
    });
    mainApp.controller('application',function($scope){
      $scope.message = "Welcome to Angularjs";
    });
});
