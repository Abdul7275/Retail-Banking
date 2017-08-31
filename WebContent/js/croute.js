var CApp = angular.module("CApp", ['ngRoute']);
CApp.config(function($routeProvider) {
    $routeProvider
    .when('/home', {
        templateUrl : 'pages/chome.html',
     
    })
    .when('/deposit', {
        templateUrl : 'pages/deposit.html',
        controller: 'Deposit'
    })
    .when('/withdraw', {
        templateUrl : 'pages/withdraw.html',
        controller: 'Withdraw'
    })
    .when('/transfer', {
        templateUrl : 'pages/transfer.html',
        controller: 'Transfer'
    })
    .when('/getAcc', {
        templateUrl : 'pages/getAccount.html',
        controller: 'GetAccount'
    })
    .when('/getStatement', {
        templateUrl : 'pages/getStatement.html',
        controller: 'GetStatement'
    })
  
    .otherwise({
      redirectTo: '/home',
    });
 
});
