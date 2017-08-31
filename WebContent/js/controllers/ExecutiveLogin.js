homeApp.controller("ExecutiveLogin", function ($scope, $http, $templateCache) {
    $scope.l={"username":"alok","password":"jadu"};
    $scope.l.message="initial message";
//login function
     $scope.l.loginExecutive = function() {
     $scope.code = null;
     $scope.response = null;
     var data1=$scope.l;
     console.log('username :'+data1.username);
     console.log('password :'+data1.password);

     $http({method: 'POST', url: '/ABC_Retail_Banking/c_login',data: { username: data1.username,password:data1.password}, cache: $templateCache}).
       then(function(response) {
         $scope.status = response.status;
         if($scope.status===200){
       	  $scope.l.message=response.data.message; 
         }
         $scope.data = response.data;
       }, function(response) {
         $scope.data = response.data || 'Request failed';
         $scope.status = response.status;
         $scope.l.message="error on server || request failed"; 
     });
   };
     
});