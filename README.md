MortgageComparerProject
=======================

Android Mortgage Comparer

This is a very simple adaptation of a c# script I had created when shopping around for Mortgages.  I couldn't find any mortgage calculators around that would do exactly what I wanted, so I created my own.

Some technologies employed in this project include:

* Android Compatibility Library to target API 7 with ActionBar and Fragments
* Sonar violations eliminated with local Sonar analysis
* Exception reporting done using ACRA to Cloudant for production and to a local CouchDB/Acralyzer for development
* Configurable splash screen with optional background processing
* SQLite database used for data needs
* Apache commons library used for some utility functions
* Test-driven development with MortgageComparerTest project using Robolectric and EclEmma used for Code Coverage.
* Shadow classes created for unit tests and better coverage
* Used a custom Bus (Otto) for cleaner Activity/Fragments communications

If you wish to hire me, my resume is at www.kolbly.com
