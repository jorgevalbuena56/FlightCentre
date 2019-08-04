This solution was made using MVVM and the following technologies

1) Data binding for the view Layer
2) Dependency injection with Kodein
3) Coroutines to handle asych calls to fetch the information from the server
4) Room to store the information in Database
5) Retrofit to connect with the server

The basic architecture is as follows:

The FlightCentreApplication class contains the initialization of all the object used across the app:
repository, DAO, view models, etc. To avoid a tight coupling them in the other classes, dependency injection is
used to maintain those objects creation encapsulated here. Kodein is the library that is being used for DI

In the main activity, the view is attached using data binding from the xml file: activity_main
The main activity uses the FlightMainListViewModel class and in conjunction with
FlightMainListViewModelFactory I am making sure to retain the information across lifecycle changes
(device orientation, app in background, etc) and to prevent bugs from async tasks that can be running
during those unexpected lifecycle changes.

Once the view is loaded, I am attaching an observer to the flight list that is handle by the view model.
The loading of the list is triggered in a lazy mode with a custom lunch declared in ScopedActivity,
using corutines and Dispatchers to specify the threads to be used.

The flight List is a LiveData object that is populated when the method getFlights is called
 from the Repository.

The FlightCenterRepository is an interface, to decouple implementations.
The flightCenterRepositoryImpl is the concrete implementation that uses FlightDetailDataSource to connect to the URL
and FlightDAO to store the information in local DB using room.

FlightDetailsAPIService encapsulates the logic to initialize Retrofit, setup the URL and connect asynchronously
with the server.

FlightDetails is used as data object class to stored the information from the server and defines the table structure used
in DB.
FlightDAO contains the queries done against the DB
FlightDatabase handles the initialization of the DB and is a singleton and thread aware

ConnectivityInterceptor is a class used in conjunction with retrofit to detect when there's no internet connectivity

Basid Diagram of the arquitecture used:

           Activity -> ViewModel -> Repository -> DAO, DataSource -> APIService
                           |            |                |
                       LiveData      LiveData        LiveData








