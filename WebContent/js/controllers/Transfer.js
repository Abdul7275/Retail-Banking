CApp.controller("Transfer", function ($scope, $http, $templateCache) {
     $scope.dep={};
     $scope.dep.depSuccess=false;
     $scope.dep.successResult=false;
     $scope.dep.depform=true;
     $scope.dep.depositing=false;
     $scope.dep.prev_B=0;
     $scope.dep.cr_B=0;
     //alert("deposit controller working");
     console.log('inside withdraw controller working');
   //write function for validating then run function only when validations are correct
     $scope.dep.submitForm = function() {
    	 console.log('inside withdraw controller working');
         // check to make sure the form is completely valid
    	 //everything is correct then run function from it
         if ($scope.CCForm.$valid) {
             
           
             //run create Customer function
             $scope.dep.deposit();
         }

     };
     
     
      $scope.dep.deposit = function() {
      $scope.dep.depositing=true;
      $scope.dep.form1=true;
      $scope.code = null;
      $scope.response = null;
      var data1=$scope.dep;
      console.log('Account ID :'+data1.account_ID);
      console.log('Target Account ID :'+data1.t_account_ID);
      console.log('Amount :'+data1.amount);

      $http({method: 'POST', url: '/ABC_Retail_Banking/transfer',data: { account_ID: data1.account_ID,t_account_ID:data1.t_account_ID, amount: data1.amount }, cache: $templateCache}).
        then(function(response) {
          $scope.status = response.status;
          if($scope.status===200){
        	  $scope.dep.message=response.data.message;
        	  $scope.dep.prev_B=response.data.prev_B;
        	  $scope.dep.cr_B=response.data.cr_B;
        	  
        	  $scope.dep.customer_ID=response.data.customer_ID;
        	  $scope.dep.t_prev_B=response.data.t_prev_B;
        	  $scope.dep.t_cr_B=response.data.t_cr_B;
        	 
        	  $scope.dep.t_customer_ID=response.data.t_customer_ID;
        	  $scope.dep.message=response.data.message;
        	 
        	  $scope.dep.depSuccess=true;
        	  
        	  $scope.dep.depositing=false;
        	  if(response.data.err==="false"){
        		  $scope.dep.successResult=true;
        		  $scope.dep.depform=false;
        	  }
        	  
        	  
        	  
        
        	  
          }
          $scope.data = response.data;
          $scope.dep.message1=response.data.message;
        }, function(response) {
          $scope.data = response.data || 'Request failed';
          $scope.status = response.status;
          $scope.dep.depositing=false;
          $scope.dep.account_ID=response.data.account_ID;
          $scope.dep.t_account_ID=response.data.t_account_ID;
          $scope.dep.message="error on server";
      });
    };
      
});
