/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.portfolio.self.registration.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.portfolio.self.registration.SelfServiceApiConstants;
import org.apache.fineract.portfolio.self.registration.service.SelfServiceRegistrationWritePlatformService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.springframework.stereotype.Component;

@Path("/v1/self/registration")
@Component
@Tag(name = "Self Service Registration", description = "")
@RequiredArgsConstructor
public class SelfServiceRegistrationApiResource {

    private final SelfServiceRegistrationWritePlatformService selfServiceRegistrationWritePlatformService;
    private final DefaultToApiJsonSerializer<AppUser> toApiJsonSerializer;

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String createSelfServiceRegistrationRequest(final RegistrationRequestData requestData) {
        // Implement code to capture signing mandate, employment status, photo, ID, signature, etc.
        this.selfServiceRegistrationWritePlatformService.createRegistrationRequest(requestData);
        return SelfServiceApiConstants.createRequestSuccessMessage;
    }

    @POST
    @Path("/user")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String createSelfServiceUser(final UserData userData) {
        // Implement code to create user with captured information
        AppUser user = this.selfServiceRegistrationWritePlatformService.createUser(userData);
        return this.toApiJsonSerializer.serialize(CommandProcessingResult.resourceResult(user.getId()));
    }

    @POST
    @Path("/upload/photo")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    public String uploadPhoto(final PhotoUploadData photoData) {
        // Implement code to handle photo upload
        return "Photo uploaded successfully";
    }

     @POST
    @Path("/upload/photo")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    public Response uploadPhoto(final PhotoUploadData photoData) {
        if (photoData == null || photoData.getPhoto() == null) {
            return Response.status(Status.BAD_REQUEST).entity("Photo data is missing").build();
        }
        
        if (photoData.getPhoto().length > 100 * 1024) { // 100KB limit
            return Response.status(Status.BAD_REQUEST).entity("Photo size exceeds the limit of 100KB").build();
        }
        
        return Response.ok().entity("Photo uploaded successfully").build();
    }

    @POST
    @Path("/upload/id")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    public Response uploadId(final IdUploadData idData) {
        
        return Response.ok().entity("ID uploaded successfully").build();
    }

    @POST
    @Path("/upload/signature")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    public String uploadSignature(final SignatureUploadData signatureData) {
        // Implement code to handle signature upload
        return "Signature uploaded successfully";
    }
}
