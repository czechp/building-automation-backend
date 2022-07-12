package app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain;

public enum DeviceEventType {
    CREATE {
        @Override
        public String getFullName() {
            return CREATE.removeWhiteSpaces();
        }
    },
    DELETE {
        @Override
        public String getFullName() {
            return DELETE.removeWhiteSpaces();
        }
    },
    NEW_STATE_REQUEST {
        @Override
        public String getFullName() {
            return NEW_STATE_REQUEST.removeWhiteSpaces();
        }
    },
    FEEDBACK_FROM_DEVICE {
        @Override
        public String getFullName() {
            return FEEDBACK_FROM_DEVICE.removeWhiteSpaces();
        }
    },

    REJECTED_MESSAGE {
        @Override
        public String getFullName() {
            return REJECTED_MESSAGE.removeWhiteSpaces();
        }
    };

    public abstract String getFullName();

    private String removeWhiteSpaces() {
        return this.toString().replace("_", " ");
    }
}
