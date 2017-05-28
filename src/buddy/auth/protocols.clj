;; Copyright 2013-2016 Andrey Antukh <niwi@niwi.nz>
;;
;; Licensed under the Apache License, Version 2.0 (the "License")
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;;     http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(ns buddy.auth.protocols
  "Main authentication and authorization abstractions
  defined as protocols.")

(defprotocol IAuthentication
  "Protocol that defines unified workflow steps for
  all authentication backends."
  (-parse [_ request]
    "Parse token from the request. If it returns `nil`
    the `authenticate` phase will be skipped and the
    handler will be called directly.")
  (-authenticate [_ request data]
    "Given a request and parsed data (from previous step),
    try to authenticate this data.

    If this method returns not nil value, the request
    will be considered authenticated and the value will
    be attached to request under `:identity` attribute."))

(defprotocol IAuthorization
  "Protocol that defines unified workflow steps for
  authorization exceptions."
  (-handle-unauthorized [_ request metadata]
    "This function is executed when a `NotAuthorizedException`
    exception is intercepted by authorization wrapper.

    It should return a valid ring response."))

(defprotocol IAuthorizationdError
  "Abstraction that allows the user to extend the exception
  based authorization system with own types."
  (-get-error-data [_] "Get error information."))
