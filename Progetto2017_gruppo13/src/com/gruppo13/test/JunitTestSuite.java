package com.gruppo13.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses(
		{TestCancellazione.class,
			TestEsecuzione.class,
			TestRegistraServizio.class,
			TestRicerca.class,
			TestVisualizzazione.class
		})



public class JunitTestSuite {
	
}
