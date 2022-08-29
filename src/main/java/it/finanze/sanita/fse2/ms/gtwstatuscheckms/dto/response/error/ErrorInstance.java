package it.finanze.sanita.fse2.ms.gtwstatuscheckms.dto.response.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorInstance {

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Validation {
                public static final String CONSTRAINT_FIELD = "/constraint/field";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Server {
                public static final String INTERNAL = "/internal";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Resource {
                public static final String NOT_FOUND = "/not-found";
        }

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static final class Fields {
                public static final String DATA_DA = "dataDa";
                public static final String DATA_PERIOD = "intervalloData";
        }

}
