mainApp.controller("CreateAccountController", function ($scope, $http, $templateCache) {
     $scope.acc={"customer_ID":"","acc_type":"","balance":""};
     $scope.acc.su=false;
     $scope.acc.fail=false;
     $scope.acc.form1=true;
     $scope.acc.creating=false;
     
      $scope.acc.createAccount = function() {
      $scope.acc.creating=true;
      $scope.acc.form1=true;
      $scope.code = null;
      $scope.response = null;
      $scope.acc.su=false;
      var data1=$scope.acc;
      console.log('Customer ID :'+data1.customer_ID);
      console.log('Account Type :'+data1.acc_type);
      console.log('Balance :'+data1.balance);

      $http({method: 'POST', url: '/ABC_Retail_Banking/createAcc',data: { customer_ID: data1.customer_ID, acc_type: data1.acc_type, balance:data1.balance }, cache: $templateCache}).
        then(function(response) {
          $scope.status = response.status;
          console.log("error sts"+response.data.err);
          if($scope.status===200 && response.data.err!=undefined){
        	  $scope.acc.message='Account Created Succesfully with Account_ID : '+response.data.Account_ID;
        	  $scope.acc.su=true;
        	  $scope.acc.form1=false;
        	  $scope.acc.creating=false;
        	  console.log('error state'+response.data.err);
        	  console.log('Account_ID is : '+response.data.Account_ID);
        	  
          }
          if(response.data.err===undefined){
          $scope.data = response.data;
          console.log('error state'+response.data.err);
          $scope.acc.message="user creation failed ";
          $scope.acc.creating=false;
          $scope.acc.fail=true;
          }
        }, function(response) {
          $scope.data = response.data || 'Request failed';
          $scope.status = response.status;
          $scope.acc.message="user creation Request failed ";
          $scope.acc.fail=true;
      });
    };
      
});
