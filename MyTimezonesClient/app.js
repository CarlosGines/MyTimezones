(function() {
  angular.module('app', [])
  .controller('appCtrl', ['$http', '$scope', function($http, $scope) {
    console.log("Controller loaded");

    var host = "http://192.168.1.21:8080";
    var token_foo = "668d2ce3-327c-4cd7-9b1e-b3a6daf8a512";
    var tzId = "5706585e2e9d7290e56592a9";
    var config = function() {
      return {
        headers: {
              'Authorization': 'Bearer ' + $scope.token,
        } 
      }
    }

    $scope.res = "No response yet";

    $scope.register = function() {
      var route = "/register"
      var body = {
        username: $scope.username,
        password: $scope.password
      }
      sendPost(route, body, function(err, res) {
        if(res) {
          $scope.token = res.data.token;
        }
      });
    }

    $scope.signin = function() {
      var route = "/signin"
      var body = {
        username: $scope.username,
        password: $scope.password
      }
      sendPost(route, body, function(err, res) {
        if(res) {
          $scope.token = res.data.token;
        }
      });
    }

    $scope.createTz = function() {
      var route = "/timezones";
      var body = {
        name: $scope.name,
        city: $scope.city,
        timeDiff: $scope.timeDiff
      };
      sendPost(route, body, function(err, res) {
        if(res) {
          $scope._id = res.data._id
        }
      });
    }

    this.getTzs = function() {
      var route = "/timezones";
      sendGet(route);
    }

    this.filterTzs = function() {
      var route = "/timezones/filter/" + $scope.term ;
      sendGet(route);
    }

    this.getTz = function() {
      var route = "/timezones/" + $scope._id;
      sendGet(route);
    }

    this.updateTz = function() {
      var body = {
        name: $scope.name, 
        city: $scope.city,
        timeDiff: $scope.timeDiff
      };
      var route = "/timezones/" + $scope._id;
      sendPut(route, body);
    }

    this.deleteTz = function() {
      var route = "/timezones/" + $scope._id;
      sendDelete(route);
    }

    var sendGet = function(route, cb) {
      console.log("Sending GET request to " + host + route);
      $http.get(host + route, config())
      .then(
        function (res) {
          if(cb) cb(null, res);
          printResponse(res);
        }, 
        function (res) {
          if(cb) cb(res, null);
          printError(res);
        }
      );    }

    var sendPost = function(route, body, cb) {
      console.log("Sending POST request to " + host + route + " with body:");
      console.log(body);
      $http.post(host + route, body, config())
      .then(
        function (res) {
          if(cb) cb(null, res);
          printResponse(res);
        }, 
        function (res) {
          if(cb) cb(res, null);
          printError(res);
        }
      );
    };

    var sendPut = function(route, body, cb) {
      console.log("Sending PUT request to " + host + route + " with body:");
      console.log(body);
      $http.put(host + route, body, config())
      .then(
        function (res) {
          if(cb) cb(null, res);
          printResponse(res);
        }, 
        function (res) {
          if(cb) cb(res, null);
          printError(res);
        }
      );    
    }

    var sendDelete = function(route, cb) {
      console.log("Sending DELETE request to " + host + route);
      $http.delete(host + route, config())
      .then(
        function (res) {
          if(cb) cb(null, res);
          printResponse(res);
        }, 
        function (res) {
          if(cb) cb(res, null);
          printError(res);
        }
      );
    }

    var printResponse = function(res) {
      console.log("Response:");
      console.log(res);
      $scope.res = JSON.stringify(res, null, 2);
    }
    var printError = function(res) {
      console.log("Error:");
      console.error(res);
      $scope.res = JSON.stringify(res, null, 2);
    }
	}]);	
})();