'use strict';

angular.module('services.authentication', ['ngCookies', 'LocalStorageModule']);

angular.module('services.authentication')
    .factory('AuthService', ['Base64', 'localStorageService', '$http', '$q','$rootScope', function (Base64, localStorageService, $http, $q,$rootScope) {

        function setCredentials(username, password) {
            localStorageService.add('authdata', Base64.encode(username + ':' + password));
            loadAuthHeader();
        }

        $http.defaults.headers.common['Authorization'] = '';

        function clearCredentials() {
            document.execCommand("ClearAuthenticationCache");
            localStorageService.remove('authdata');
            $http.defaults.headers.common['Authorization'] = '';
        }

        function loadAuthHeader() {
            var authdata = localStorageService.get('authdata');
            if (authdata) {
                $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata;
            }
        }

        function getCurrentUserFromRestApi() {
            loadAuthHeader();
            var authdata = localStorageService.get('authdata');
            if (authdata) {
                var deferredUserFromApi = $q.defer();
                $http.get("../secure/user/current").then(
                    function (response) {
                        currentUser = response.data;
                        currentUser.isAdmin = function(){
                            return  currentUser.roles.indexOf("ROLE_ADMIN") !== -1; 
                        };
                        $rootScope.currentUser = currentUser;
                        deferredUserFromApi.resolve(response.data);
                    }, function (error) {
                        deferredUserFromApi.reject(error);
                    }
                );
                return deferredUserFromApi.promise;
            } else {
                return $q.reject({state: 401, message: "Login Failed"});
            }
        }

        var currentUser = undefined;

        return {
            /*
             This function returns the cached user if available otherwhise it checkst the current-user rest-endpoint
             returns a promise
             */
            getCurrentUser: function () {
                if (currentUser) {
                    var deferred = $q.defer();
                    deferred.resolve(currentUser);
                    return deferred.promise;
                } else {
                    return getCurrentUserFromRestApi();
                }

            },
            loadAuthHeader: function () {
                loadAuthHeader();
            },
            login: function (username, password) {
                setCredentials(username, password);
                return getCurrentUserFromRestApi();
            },
            logout: function () {
                clearCredentials();
                currentUser = undefined;
                $rootScope.currentUser = undefined;
            }
        };
    }])
    .factory('Base64', function () {
        var keyStr = 'ABCDEFGHIJKLMNOP' +
            'QRSTUVWXYZabcdef' +
            'ghijklmnopqrstuv' +
            'wxyz0123456789+/' +
            '=';
        return {
            encode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                do {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);

                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;

                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }

                    output = output +
                        keyStr.charAt(enc1) +
                        keyStr.charAt(enc2) +
                        keyStr.charAt(enc3) +
                        keyStr.charAt(enc4);
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
                } while (i < input.length);

                return output;
            },

            decode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
                var base64test = /[^A-Za-z0-9\+\/\=]/g;
                if (base64test.exec(input)) {
                    alert("There were invalid base64 characters in the input text.\n" +
                        "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                        "Expect errors in decoding.");
                }
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

                do {
                    enc1 = keyStr.indexOf(input.charAt(i++));
                    enc2 = keyStr.indexOf(input.charAt(i++));
                    enc3 = keyStr.indexOf(input.charAt(i++));
                    enc4 = keyStr.indexOf(input.charAt(i++));

                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;

                    output = output + String.fromCharCode(chr1);

                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }

                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";

                } while (i < input.length);

                return output;
            }
        };
    });
