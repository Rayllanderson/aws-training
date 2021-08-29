package com.rayllanderson.aws

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.rayllanderson.aws")
		.start()
}

