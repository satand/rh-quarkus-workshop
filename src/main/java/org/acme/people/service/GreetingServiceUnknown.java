package org.acme.people.service;

import jakarta.enterprise.context.ApplicationScoped;

@Locale(Language.UNKNOWN)
@ApplicationScoped
public class GreetingServiceUnknown extends GreetingService {}