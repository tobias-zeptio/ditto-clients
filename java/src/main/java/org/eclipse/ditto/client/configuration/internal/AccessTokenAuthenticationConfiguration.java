/*
 * Copyright (c) 2019 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.client.configuration.internal;

import static org.eclipse.ditto.model.base.common.ConditionChecker.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.client.authentication.internal.AccessTokenSupplier;
import org.eclipse.ditto.client.configuration.AuthenticationConfiguration;
import org.eclipse.ditto.client.configuration.ProxyConfiguration;

/**
 * A {@link org.eclipse.ditto.client.configuration.AuthenticationConfiguration} for access token authentication.
 *
 * @since 1.0.0
 */
@Immutable
public final class AccessTokenAuthenticationConfiguration extends AbstractAuthenticationConfiguration {

    private final String identifier;
    private final AccessTokenSupplier accessTokenSupplier;

    private AccessTokenAuthenticationConfiguration(final String identifier,
            final AccessTokenSupplier accessTokenSupplier,
            final Map<String, String> additionalHeaders) {
        super(identifier, additionalHeaders, null);
        this.identifier = identifier;
        this.accessTokenSupplier = accessTokenSupplier;
    }

    /**
     * @return a new builder to build {@code AccessTokenAuthenticationConfiguration}.
     */
    public static AccessTokenAuthenticationConfigurationBuilder newBuilder() {
        return new AccessTokenAuthenticationConfigurationBuilder();
    }

    /**
     * Returns the identifier.
     *
     * @return the identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the access token supplier.
     *
     * @return the supplier.
     */
    public AccessTokenSupplier getAccessTokenSupplier() {
        return accessTokenSupplier;
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final AccessTokenAuthenticationConfiguration that = (AccessTokenAuthenticationConfiguration) o;
        return Objects.equals(identifier, that.identifier) &&
                Objects.equals(accessTokenSupplier, that.accessTokenSupplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), identifier, accessTokenSupplier);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" +
                super.toString() +
                ", identifier=" + identifier +
                ", accessTokenSupplier=" + accessTokenSupplier +
                "]";
    }

    @NotThreadSafe
    public static class AccessTokenAuthenticationConfigurationBuilder
            implements AuthenticationConfiguration.Builder {

        private String identifier;
        private AccessTokenSupplier accessTokenSupplier;
        private final Map<String, String> additionalHeaders = new HashMap<>();

        /**
         * Sets the identifier to authenticate.
         *
         * @param identifier the identifier.
         * @return this builder.
         */
        public AccessTokenAuthenticationConfigurationBuilder identifier(final String identifier) {
            this.identifier = identifier;
            return this;
        }

        /**
         * Sets the access token supplier to authenticate.
         *
         * @param accessTokenSupplier the supplier.
         * @return this builder.
         */
        public AccessTokenAuthenticationConfigurationBuilder accessTokenSupplier(
                final AccessTokenSupplier accessTokenSupplier) {
            this.accessTokenSupplier = accessTokenSupplier;
            return this;
        }

        @Override
        public AccessTokenAuthenticationConfigurationBuilder withAdditionalHeader(final String key,
                final String value) {
            additionalHeaders.put(checkNotNull(key, "key"), value);
            return this;
        }

        @Override
        public AccessTokenAuthenticationConfigurationBuilder proxyConfiguration(
                final ProxyConfiguration proxyConfiguration) {
            return this;
        }

        @Override
        public AccessTokenAuthenticationConfiguration build() {
            return new AccessTokenAuthenticationConfiguration(identifier, accessTokenSupplier, additionalHeaders);
        }

    }

}
